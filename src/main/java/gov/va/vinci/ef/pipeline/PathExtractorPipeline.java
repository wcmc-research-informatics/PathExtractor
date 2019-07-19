package gov.va.vinci.ef.pipeline;

/*
 * #%L
 * Echo concept exctractor
 * %%
 * Copyright (C) 2010 - 2016 Department of Veterans Affairs
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import gov.va.vinci.ef.ae.*;
import gov.va.vinci.leo.conceptlink.ae.ConceptLinkAnnotator;
import gov.va.vinci.leo.conceptlink.ae.MatchMakerAnnotator;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.filter.ae.FilterAnnotator;
import gov.va.vinci.leo.regex.ae.RegexAnnotator;
import gov.va.vinci.leo.types.TypeLibrarian;
import gov.va.vinci.leo.window.ae.WindowAnnotator;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.impl.TypeDescription_impl;

import java.util.regex.Pattern;

/**
 * Based on the Pipeline class this pipeline is a scalable, optimized echo extraction pipeline that attempts to zero in
 * on concepts and measurements as fast as possible.
 * <p>
 * Created by thomasginter on 07/28/2015.
 * Updated by olga patterson on 06/14/2016
 * Updated by prakash adekkanattu on 04/03/2017
 */
public class PathExtractorPipeline implements PipelineInterface {
  LeoAEDescriptor pipeline = null;
  LeoTypeSystemDescription description = null;

  protected static String RESOURCE_PATH = "resources/";

  public PathExtractorPipeline() {
    this.getLeoTypeSystemDescription();
  }

  @Override
  public LeoAEDescriptor getPipeline() throws Exception {
    if (pipeline != null)
      return pipeline;

    //Build the pipeline
    LeoTypeSystemDescription types = getLeoTypeSystemDescription();
    pipeline = new LeoAEDescriptor();
    
    /*******************************
    //Pipeline for TNM Stage:
    ********************************/
    
    // Module 1: MeasurementRegex1 -- find mentions of TNM staging 
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "tnmMeasure.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setOutputType("gov.va.vinci.ef.types.TNMMeasurement")
        .setName("TNMMeasurementRegex")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    // Module 2: WindowAnnotator -- make a context window of -7...+7 tokens around the TNM measurement phrase
    pipeline.addDelegate(new WindowAnnotator()
        .setAnchorFeature("Anchor")
        .setWindowSize(7)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.TNMMeasurement"})
        .setOutputType("gov.va.vinci.ef.types.TNMMeasurementWindow")
        .setName("TNMWindowAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 3: AnatomyAnnotator -- find an anatomy phrase in the window around measurement
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "tnmAnatomy.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.TNMMeasurementWindow"})
        .setOutputType("gov.va.vinci.ef.types.TNMAnatomy")
        .setName("TNMAnatomyAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 4: ConceptCollector -- merge anatomy and measurement annotations into a single annotation
    pipeline.addDelegate(new ConceptLinkAnnotator()
        .setIncludeChildren(true)
        .setIncludeWhiteSpace(false)
        .setMaxDifference(2)
        .setMaxDistance(20)
        .setPatternFile(RESOURCE_PATH + "tnmConceptCollection.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.TNMAnatomy", "gov.va.vinci.ef.types.TNMMeasurement"})
        .setOutputType("gov.va.vinci.ef.types.TNMMeasurement")
        .setName("TNMConceptCollector")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 5: ContextWindowAnnotator -- create a window of 0 ... +80 tokens around measurement phrase
    pipeline.addDelegate(new WindowAnnotator()
        .setAnchorFeature("Anchor")
        .setLtWindowSize(0)
    	.setRtWindowSize(80)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.TNMMeasurement"})
        .setOutputType("gov.va.vinci.ef.types.TNMContextWindow")
        .setName("TNMContextWindowAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 6: NumericValueAnnotator -- find numeric value in the context window
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "tnmNumericValue.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.TNMContextWindow"})
        .setOutputType("gov.va.vinci.ef.types.TNMNumericValue")
        .setName("TNMNumericValueAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 7: MergeRangeValueAnnotator - create a range annotation when the value is expressed as a range
    pipeline.addDelegate(new ConceptLinkAnnotator()
        .setIncludeChildren(true)
        .setMaxCollectionSize(2)
        .setMaxDifference(0)
        .setMaxDistance(10)
        .setPatternFile(RESOURCE_PATH + "tnmRange.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.TNMNumericValue"})
        .setOutputType("gov.va.vinci.ef.types.TNMNumericValue")
        .setName("TNMMergeRangeValueAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 8: NumericMeasureFilter -- Filter out all numeric values that do not meet context criteria
    pipeline.addDelegate(new NumericMeasureFilter()
        .setRegexFilePath(RESOURCE_PATH + "tnmInvalidNumeric.regex")
        .setName("TNMNumericMeasureFilter")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 9: EFValidatorAnnotator -- Filter out all numeric values that do not meet valid range criteria
    pipeline.addDelegate(new EFValidatorAnnotator()
        .setRemoveIfAnyInvalid(false)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.TNMNumericValue"})
        .setName("TNMValidatorAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 10: MeasureToValueRelationAnnotator -- Create LVEF concept-value pair based on the relational patterns between measurement and value
    pipeline.addDelegate(new MatchMakerAnnotator()
        .setConceptTypeName("gov.va.vinci.ef.types.TNMMeasurement")
        .setValueTypeName("gov.va.vinci.ef.types.TNMNumericValue")
        .setPeekRightFirst(true)
        .setMaxCollectionSize(2)
        .setMaxDifference(2)
        .setMaxDistance(50)
        .setPatternFile(RESOURCE_PATH + "tnmMiddleStuff.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.TNMMeasurement",
            "gov.va.vinci.ef.types.TNMNumericValue"})
        .setOutputType("gov.va.vinci.ef.types.TNMRelation")
        .setName("TNMMeasureToValueRelationAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    
    /*******************************
    //Pipeline for ICD:  
    ********************************/
    
    // Module 1: MeasuremntRegex2 -- find mentions of ICD codes
    pipeline.addDelegate(new RegexAnnotator()
            .setResource(RESOURCE_PATH + "icdMeasure.regex")
            .setCaseSensitive(false)
            .setPatternFlags(Pattern.DOTALL)
            .setOutputType("gov.va.vinci.ef.types.ICDMeasurement")
            .setName("ICDMeasurementRegex")
            .addLeoTypeSystemDescription(types)
            .getDescriptor());
    // Module 2: WindowAnnotator -- make a context window of -7...+7 tokens around the ICD measurement phrase
    pipeline.addDelegate(new WindowAnnotator()
            .setAnchorFeature("Anchor")
            .setWindowSize(7)
            .setInputTypes(new String[]{"gov.va.vinci.ef.types.ICDMeasurement"})
            .setOutputType("gov.va.vinci.ef.types.ICDMeasurementWindow")
            .setName("ICDWindowAnnotator")
            .addLeoTypeSystemDescription(types)
            .getDescriptor());   
    // Module 3: AnatomyAnnotator -- find an anatomy phrase in the window around measurement
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "icdAnatomy.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.ICDMeasurementWindow"})
        .setOutputType("gov.va.vinci.ef.types.ICDAnatomy")
        .setName("ICDAnatomyAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    // Module 4: ConceptCollector -- merge anatomy and measurement annotations into a single annotation
    pipeline.addDelegate(new ConceptLinkAnnotator()
        .setIncludeChildren(true)
        .setIncludeWhiteSpace(false)
        .setMaxDifference(2)
        .setMaxDistance(20)
        .setPatternFile(RESOURCE_PATH + "icdConceptCollection.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.ICDAnatomy", "gov.va.vinci.ef.types.ICDMeasurement"})
        .setOutputType("gov.va.vinci.ef.types.ICDMeasurement")
        .setName("ICDConceptCollector")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    // Module 5: ContextWindowAnnotator -- create a window of 0 ... +80 tokens around measurement phrase
    pipeline.addDelegate(new WindowAnnotator()
        .setAnchorFeature("Anchor")
        .setLtWindowSize(0)
    	.setRtWindowSize(80)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.ICDMeasurement"})
        .setOutputType("gov.va.vinci.ef.types.ICDContextWindow")
        .setName("ICDContextWindowAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    // Module 6: NumericValueAnnotator -- find numeric value in the context window
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "icdNumericValue.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.ICDContextWindow"})
        .setOutputType("gov.va.vinci.ef.types.ICDNumericValue")
        .setName("ICDNumericValueAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    // Module 7: MergeRangeValueAnnotator - create a range annotation when the value is expressed as a range
    pipeline.addDelegate(new ConceptLinkAnnotator()
        .setIncludeChildren(true)
        .setMaxCollectionSize(2)
        .setMaxDifference(0)
        .setMaxDistance(10)
        .setPatternFile(RESOURCE_PATH + "icdRange.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.ICDNumericValue"})
        .setOutputType("gov.va.vinci.ef.types.ICDNumericValue")
        .setName("ICDMergeRangeValueAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    // Module 8: NumericMeasureFilter -- Filter out all numeric values that do not meet context criteria
    pipeline.addDelegate(new NumericMeasureFilter()
        .setRegexFilePath(RESOURCE_PATH + "icdInvalidNumeric.regex")
        .setName("ICDNumericMeasureFilter")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    // Module 9: EFValidatorAnnotator -- Filter out all numeric values that do not meet valid range criteria
    pipeline.addDelegate(new EFValidatorAnnotator()
        .setRemoveIfAnyInvalid(false)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.ICDNumericValue"})
        .setName("ICDValidatorAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    // Module 10: MeasureToValueRelationAnnotator -- Create ICD concept-value pair based on the relational patterns between measurement and value
    pipeline.addDelegate(new MatchMakerAnnotator()
        .setConceptTypeName("gov.va.vinci.ef.types.ICDMeasurement")
        .setValueTypeName("gov.va.vinci.ef.types.ICDNumericValue")
        .setPeekRightFirst(true)
        .setMaxCollectionSize(2)
        .setMaxDifference(2)
        .setMaxDistance(50)
        .setPatternFile(RESOURCE_PATH + "icdMiddleStuff.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.ICDMeasurement",
            "gov.va.vinci.ef.types.ICDNumericValue"})
        .setOutputType("gov.va.vinci.ef.types.ICDRelation")
        .setName("ICDMeasureToValueRelationAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
  
    /*******************************
    // Pipeline for Gleason Score:
    ********************************/      	  
	 // Module 1: MeasuremntRegex3 -- find mentions of gleason score
	 pipeline.addDelegate(new RegexAnnotator()
      .setResource(RESOURCE_PATH + "gsMeasure.regex")
      .setCaseSensitive(false)
      .setPatternFlags(Pattern.DOTALL)
      .setOutputType("gov.va.vinci.ef.types.GSMeasurement")
      .setName("MeasurementRegex3")
      .addLeoTypeSystemDescription(types)
      .getDescriptor());
	 
	 // Module 2: WindowAnnotator -- make a context window of -7...+7 tokens around the GS measurement phrase
    pipeline.addDelegate(new WindowAnnotator()
        .setAnchorFeature("Anchor")
        .setWindowSize(7)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.GSMeasurement"})
        .setOutputType("gov.va.vinci.ef.types.GSMeasurementWindow")
        .setName("GSWindowAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
	
	 // Module 2: AnatomyAnnotator -- find an anatomy phrase in the window around measurement
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "gsAnatomy.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.GSMeasurementWindow"})
        .setOutputType("gov.va.vinci.ef.types.GSAnatomy")
        .setName("GSAnatomyAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    // Module 4: ConceptCollector -- merge anatomy and measurement annotations into a single annotation
    pipeline.addDelegate(new ConceptLinkAnnotator()
        .setIncludeChildren(true)
        .setIncludeWhiteSpace(false)
        .setMaxDifference(2)
        .setMaxDistance(20)
        .setPatternFile(RESOURCE_PATH + "gsConceptCollection.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.GSAnatomy", "gov.va.vinci.ef.types.GSMeasurement"})
        .setOutputType("gov.va.vinci.ef.types.GSMeasurement")
        .setName("GSConceptCollector")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    // Module 5: ContextWindowAnnotator -- create a window of 0 ... +80 tokens around measurement phrase
    pipeline.addDelegate(new WindowAnnotator()
        .setAnchorFeature("Anchor")
        .setLtWindowSize(0)
    	.setRtWindowSize(80)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.GSMeasurement"})
        .setOutputType("gov.va.vinci.ef.types.GSContextWindow")
        .setName("GSContextWindowAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    // Module 6: NumericValueAnnotator -- find numeric value in the context window
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "gsNumericValue.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.GSContextWindow"})
        .setOutputType("gov.va.vinci.ef.types.GSNumericValue")
        .setName("GSNumericValueAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    // Module 7: MergeRangeValueAnnotator - create a range annotation when the value is expressed as a range
    pipeline.addDelegate(new ConceptLinkAnnotator()
        .setIncludeChildren(true)
        .setMaxCollectionSize(2)
        .setMaxDifference(0)
        .setMaxDistance(10)
        .setPatternFile(RESOURCE_PATH + "gsRange.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.GSNumericValue"})
        .setOutputType("gov.va.vinci.ef.types.GSNumericValue")
        .setName("GSMergeRangeValueAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    // Module 8: NumericMeasureFilter -- Filter out all numeric values that do not meet context criteria
    pipeline.addDelegate(new NumericMeasureFilter()
        .setRegexFilePath(RESOURCE_PATH + "gsInvalidNumeric.regex")
        .setName("GSNumericMeasureFilter")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    // Module 9: EFValidatorAnnotator -- Filter out all numeric values that do not meet valid range criteria
    pipeline.addDelegate(new EFValidatorAnnotator()
        .setRemoveIfAnyInvalid(false)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.GSNumericValue"})
        .setName("GSValidatorAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    // Module 10: MeasureToValueRelationAnnotator -- Create ICD concept-value pair based on the relational patterns between measurement and value
    pipeline.addDelegate(new MatchMakerAnnotator()
        .setConceptTypeName("gov.va.vinci.ef.types.GSMeasurement")
        .setValueTypeName("gov.va.vinci.ef.types.GSNumericValue")
        .setPeekRightFirst(true)
        .setMaxCollectionSize(2)
        .setMaxDifference(2)
        .setMaxDistance(50)
        .setPatternFile(RESOURCE_PATH + "gsMiddleStuff.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.GSMeasurement",
            "gov.va.vinci.ef.types.GSNumericValue"})
        .setOutputType("gov.va.vinci.ef.types.GSRelation")
        .setName("GSMeasureToValueRelationAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
     
    
    /*******************************
    // Final Module 
    ********************************/
    
    // Module: FlattenRelationAE -- Create a new type for ease of writing results to csv or database
    pipeline.addDelegate(new FlattenRelationAE()
    	.setRegexFilePath(RESOURCE_PATH + "tnmMap.regex")
    	.setName("FlattenRelationAE")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());   
    
    
 

    return pipeline;
  }


  @Override
  public LeoTypeSystemDescription getLeoTypeSystemDescription() {
    if (description != null)
      return description;
    description = new LeoTypeSystemDescription();
    String linkParent = "gov.va.vinci.leo.conceptlink.types.Link";
    // Pattern Type description
    TypeDescription regexFtsd;
    String regexParent = "gov.va.vinci.ef.types.Regex";
    regexFtsd = new TypeDescription_impl(regexParent, "", "uima.tcas.Annotation");
    regexFtsd.addFeature("pattern", "", "uima.cas.String");
    regexFtsd.addFeature("concept", "", "uima.cas.String");
    regexFtsd.addFeature("groups", "", "uima.cas.StringArray");

    // Total type definition
    try {
      description.addType(TypeLibrarian.getCSITypeSystemDescription())
          .addTypeSystemDescription(new WindowAnnotator().getLeoTypeSystemDescription())
          .addTypeSystemDescription(new ConceptLinkAnnotator().getLeoTypeSystemDescription())
          .addType(regexFtsd)
          .addType("gov.va.vinci.ef.types.EfRelation", "", linkParent)
          .addType("gov.va.vinci.ef.types.TNMRelation", "", linkParent)
          .addType("gov.va.vinci.ef.types.ICDRelation", "", linkParent)
          .addType("gov.va.vinci.ef.types.GSRelation", "", linkParent)
          .addType("gov.va.vinci.ef.types.MeasurementWindow", "", "gov.va.vinci.leo.window.types.Window")
          .addType("gov.va.vinci.ef.types.TNMMeasurementWindow", "", "gov.va.vinci.leo.window.types.Window")
          .addType("gov.va.vinci.ef.types.ICDMeasurementWindow", "", "gov.va.vinci.leo.window.types.Window")
          .addType("gov.va.vinci.ef.types.GSMeasurementWindow", "", "gov.va.vinci.leo.window.types.Window")
          .addType("gov.va.vinci.ef.types.ContextWindow", "", "gov.va.vinci.leo.window.types.Window")
          .addType("gov.va.vinci.ef.types.TNMContextWindow", "", "gov.va.vinci.leo.window.types.Window")
          .addType("gov.va.vinci.ef.types.ICDContextWindow", "", "gov.va.vinci.leo.window.types.Window")
          .addType("gov.va.vinci.ef.types.GSContextWindow", "", "gov.va.vinci.leo.window.types.Window")
         // .addType("gov.va.vinci.ef.types.NoEvidence", "", "uima.tcas.Annotation")
          .addType("gov.va.vinci.ef.types.Anatomy", "", regexParent)
          .addType("gov.va.vinci.ef.types.TNMAnatomy", "", regexParent)
          .addType("gov.va.vinci.ef.types.ICDAnatomy", "", regexParent)
          .addType("gov.va.vinci.ef.types.GSAnatomy", "", regexParent)
          .addType("gov.va.vinci.ef.types.Measurement", "", linkParent)
          .addType("gov.va.vinci.ef.types.TNMMeasurement", "", linkParent)
          .addType("gov.va.vinci.ef.types.ICDMeasurement", "", linkParent)
          .addType("gov.va.vinci.ef.types.GSMeasurement", "", linkParent)
          .addType("gov.va.vinci.ef.types.NumericValue", "", linkParent)
          .addType("gov.va.vinci.ef.types.TNMNumericValue", "", linkParent)
          .addType("gov.va.vinci.ef.types.ICDNumericValue", "", linkParent)
          .addType("gov.va.vinci.ef.types.GSNumericValue", "", linkParent)
          .addType("gov.va.vinci.kttr.types.RefSt_EfValue", "", "uima.tcas.Annotation")
          .addTypeSystemDescription(new FlattenRelationAE().getLeoTypeSystemDescription())
      ;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return description;
  }
}
