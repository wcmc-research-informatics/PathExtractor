package gov.va.vinci.ef.ae;

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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or conclied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import gov.va.vinci.ef.types.*;
import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.descriptors.LeoConfigurationParameter;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.tools.LeoUtils;
import gov.va.vinci.leo.window.WindowService;
import gov.va.vinci.leo.window.types.Window;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.impl.TypeDescription_impl;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Flatten relationships for easier output.
 * <p>
 * Created by vhaslcpatteo on 9/16/2015. Edited by Thomas Ginter on 09/18/2015.
 * Added the setValueStrings method. Prakash added several methods.
 */

public class FlattenRelationAE extends LeoBaseAnnotator {

	/**
	 * Path to the regex file to parse.
	 */
	@LeoConfigurationParameter

	protected String regexFilePath = null;

	/**
	 * Patterns for which if there is a match in the window before the anchor
	 * then the relationship is invalid.
	 */
	protected Pattern[] efPatterns = null;
	protected Pattern[] rfPatterns = null;
	
	protected Pattern valueTNMPattern = Pattern.compile("(c|y|r|a|u)?p?T(x|is|0|1(mic|a|b|c)?|2(a|b|c)?|3(a|b|c)?|4(a|b|c|d)?).{0,3}N(x|0|1(a|b)?|2(a|b|c)?|3(a|b|c)?).{0,3}M(x|0|1(a|b|c)?)", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	protected Pattern valueTNPattern = Pattern.compile("(c|y|r|a|u)?p?T(x|is|0|1(mic|a|b|c)?|2(a|b|c)?|3(a|b|c)?|4(a|b|c|d)?).{0,3}N(x|0|1(a|b)?|2(a|b|c)?|3(a|b|c)?)", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	protected Pattern valueTPattern = Pattern.compile("(c|y|r|a|u)?p?T(x|is|0|1(mic|a|b|c)?|2(a|b|c)?|3(a|b|c)?|4(a|b|c|d)?)", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	
	protected Pattern tPattern = Pattern.compile("(c|y|r|a|u)?p?T(x|is|0|1(mic|a|b|c)?|2(a|b|c)?|3(a|b|c)?|4(a|b|c|d)?)", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	protected Pattern nPattern = Pattern.compile("(c|y|r|a|u)?p?N(x|0|1(a|b)?|2(a|b|c)?|3(a|b|c)?)", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	protected Pattern mPattern = Pattern.compile("(c|y|r|a|u)?p?M(x|0|1(a|b|c)?)", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	
	protected Pattern icdPattern = Pattern.compile("(?<=ICD.{0,3}(9|10)?.{1,3}code)(.*?)(billing.{1,3}codes?:?)", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	protected Pattern icd9CodePattern = Pattern.compile("\\b((V\\d{2}(\\.\\d{1,2})?|\\d{3}(\\.\\d{1,2})?|E\\d{3}(\\.\\d)?))\\b", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	protected Pattern icd10CodePattern = Pattern.compile("\\b[A-TV-Z][0-9][A-Z0-9](\\.[A-Z0-9]{1,4})?\\b", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	
	protected Pattern gsPattern1 = Pattern.compile("(10|[0-9](\\/10)?)\\s*=?\\s*\\(?\\s*[0-5]\\s*((\\+)|\\&|\\,)\\s*[0-5]\\s*\\)?", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	protected Pattern gsPattern2 = Pattern.compile("\\(?([0-5]|I|(II)|(III)|(IV)|V)\\s*((\\+)|\\&|\\,)\\s*([0-5]|I|(II)|(III)|(IV)|V)\\s*\\)?(.*?)\\(?(=?\\s*(10|[0-9](\\/10)?))?\\)?", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	protected Pattern gsPattern3 = Pattern.compile("(10|[0-9](\\/10)?)", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	/**
	 * Window service class.
	 */
	protected WindowService windowService = new WindowService(0, 20, Window.class.getCanonicalName());
	
	
	/**
	 * Pattern flags for each regex.
	 */
	public static int PATTERN_FLAGS = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;

	/**
	 * Log messages
	 */
	private static final Logger log = Logger.getLogger(LeoUtils.getRuntimeClass());

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);

		if (StringUtils.isBlank(regexFilePath))
			throw new ResourceInitializationException("regexFilePath cannot be blank or null", null);

		try {
			parseRegexFile(new File(regexFilePath));
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public void annotate(JCas aJCas) throws AnalysisEngineProcessException {
		
		// get today's date
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss");
		// Date timeStamp = new Date();
		
		
		// System.out.println("Window: Inside relation" );
		
		// TNM relations
		Collection<TNMRelation> tnm_relations = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, TNMRelation.type, false);
		if (tnm_relations.size() > 0) {			
			for (TNMRelation relation : tnm_relations) {				
				// Create the output annotation
				Relation out = new Relation(aJCas, relation.getBegin(), relation.getEnd());
				// set time stamp 
				out.setRunDate(timeStamp);
				// add to index
				out.addToIndexes();
				// Set the string value features
				setTNMValueStrings(relation, out);												
			}						
		}
		
		// ICD relations
		Collection<ICDRelation> icd_relations = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, ICDRelation.type, false);
		if (icd_relations.size() > 0) {			
			for (ICDRelation relation : icd_relations) {				
				// Create the output annotation
				Relation out = new Relation(aJCas, relation.getBegin(), relation.getEnd());
				// set time stamp 
				out.setRunDate(timeStamp);
				// add to index
				out.addToIndexes();
				// Set the string value features
				setICDValueStrings(relation, out);												
			}						
		}
		
		// GS relations
		Collection<GSRelation> gs_relations = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, GSRelation.type, false);
		if (gs_relations.size() > 0) {			
			for (GSRelation relation : gs_relations) {				
				// Create the output annotation
				Relation out = new Relation(aJCas, relation.getBegin(), relation.getEnd());
				// set time stamp 
				out.setRunDate(timeStamp);
				// add to index
				out.addToIndexes();
				// Set the string value features
				setGSValueStrings(relation, out);												
			}						
		}
		
		
	}

	protected void setTNMValueStrings(TNMRelation in, Relation out) {

		// Get the NumericValue annotation from the merged set
		Annotation value = null;
		Annotation measure = null;
		FSArray merged = in.getLinked();
		for (int i = 0; i < merged.size(); i++) {
			Annotation a = (Annotation) merged.get(i);
			String typeName = a.getType().getName();
			if (typeName.equals(TNMMeasurement.class.getCanonicalName())) {
				measure = a;
				if (measure != null) {
					out.setTerm(measure.getCoveredText());
				}
			}else if (typeName.equals(TNMNumericValue.class.getCanonicalName())) {
				value = a;
			}
		}
		
		// Exit if no value found
		if (value == null)
			return;
		
		// Get the values
		String valueText = value.getCoveredText();
		
		// System.out.println("TNM ValueText: " + valueText);
		
		// Find Value and Value2
		
		Matcher tnm_matches = valueTNMPattern.matcher(valueText);
		ArrayList<String> tnm_values = new ArrayList<String>(2);
		while (tnm_matches.find())
			tnm_values.add(valueText.substring(tnm_matches.start(), tnm_matches.end()) );
		
		Collections.sort(tnm_values);
		
		if (tnm_values.size() > 0) {
			// Set the values
			if (tnm_values.size() > 0)
				out.setValue(tnm_values.get(0).toString());
			
			if (tnm_values.size() > 1)
				out.setValue2(tnm_values.get(tnm_values.size() - 1).toString());
		}else {
			
			Matcher tn_matches = valueTNPattern.matcher(valueText);
			ArrayList<String> tn_values = new ArrayList<String>(2);
			while (tn_matches.find())
				tn_values.add(valueText.substring(tn_matches.start(), tn_matches.end()) );
			
			Collections.sort(tn_values);
			
			if (tn_values.size() > 0) {
				// Set the values
				if (tn_values.size() > 0)
					out.setValue(tn_values.get(0).toString());
							
				if (tn_values.size() > 1)
					out.setValue2(tn_values.get(tn_values.size() - 1).toString());
			
			}else {
				
				Matcher t_matches = valueTPattern.matcher(valueText);
				ArrayList<String> t_values = new ArrayList<String>(2);
				while (t_matches.find())
					t_values.add(valueText.substring(t_matches.start(), t_matches.end()) );
				
				Collections.sort(t_values);
				
				if (t_values.size() > 0) {
					// Set the values
					if (t_values.size() > 0)
						out.setValue(t_values.get(0).toString());
								
					if (t_values.size() > 1)
						out.setValue2(t_values.get(t_values.size() - 1).toString());
				}
				
			}
			
		}
		
		// find TNM stages
		
		if (StringUtils.isNotBlank(valueText)){

			out.setValueString(valueText);
			
			Map<String, String> tnmValues = new HashMap<String, String>();
			
			tnmValues = findTNM(valueText);
			
			if(tnmValues.containsKey("t")) {
				out.setTStage(tnmValues.get("t").toString());
			}
			
			if(tnmValues.containsKey("n")) {
				out.setNStage(tnmValues.get("n").toString());
			}
			
			if(tnmValues.containsKey("m")) {
				out.setMStage(tnmValues.get("m").toString());
			}
			
		}		
		
	}
	
	
	protected void setICDValueStrings(ICDRelation in, Relation out) {

		// Get the NumericValue annotation from the merged set
		Annotation value = null;
		Annotation measure = null;
		FSArray merged = in.getLinked();
		for (int i = 0; i < merged.size(); i++) {
			Annotation a = (Annotation) merged.get(i);
			String typeName = a.getType().getName();
			if (typeName.equals(ICDMeasurement.class.getCanonicalName())) {
				measure = a;
				if (measure != null) {
					out.setTerm(measure.getCoveredText());
				}
			}else if (typeName.equals(ICDNumericValue.class.getCanonicalName())) {
				value = a;
			}
		}
		
		// Exit if no value found
		if (value == null)
			return;
		
		// Get the values
		String valueText = value.getCoveredText();
		
		// System.out.println("ICD ValueText: " + valueText);
		
		// find ICD codes
		
		if (StringUtils.isNotBlank(valueText)){

			out.setValueString(valueText);
			
			Map<String, String> icdValues = new HashMap<String, String>();
			
			icdValues = findICD(valueText);
						
			if(icdValues.containsKey("icd9")) {
				out.setICD9(icdValues.get("icd9").toString());
			}
			
			if(icdValues.containsKey("icd10")) {
				out.setICD10(icdValues.get("icd10").toString());
			}
			
		}		
		
	}
	
	
	protected void setGSValueStrings(GSRelation in, Relation out) {

		// Get the NumericValue annotation from the merged set
		Annotation value = null;
		Annotation measure = null;
		FSArray merged = in.getLinked();
		for (int i = 0; i < merged.size(); i++) {
			Annotation a = (Annotation) merged.get(i);
			String typeName = a.getType().getName();
			if (typeName.equals(GSMeasurement.class.getCanonicalName())) {
				measure = a;
				if (measure != null) {
					out.setTerm(measure.getCoveredText());
				}
			}else if (typeName.equals(GSNumericValue.class.getCanonicalName())) {
				value = a;
			}
		}
		
		// Exit if no value found
		if (value == null)
			return;
		
		// Get the values
		String valueText = value.getCoveredText();
		
		// System.out.println("GS ValueText: " + valueText);
		
		if (StringUtils.isNotBlank(valueText)){

			out.setValueString(valueText);
		
			// find Gleason Score
			Map<String, String> gsValues = new HashMap<String, String>();			
			gsValues = findGleasonScore(valueText);
			
			Boolean isGSSet = false;
			
			if (isGSSet == false){
				if(gsValues.containsKey("gs1")) {
					out.setGleasonScore(gsValues.get("gs1").toString());
					isGSSet = true;
				}
			}
			
			if (isGSSet == false){
				if (gsValues.containsKey("gs2")) {
					out.setGleasonScore(gsValues.get("gs2").toString());
					isGSSet = true;
				}
			}
			
			if (isGSSet == false){
				if (gsValues.containsKey("gs3")) {
					out.setGleasonScore(gsValues.get("gs3").toString());
					isGSSet = true;
				}
			}
			
		}		
		
	}
	
	
	
	
	/**
	 * Returns TNM values, if exist.
	 *
	 * @param patterns
	 * @param text
	 * @return
	 */
	protected Map<String, String> findTNM(String text) {
		
		Map<String, String> tnmMap = new HashMap<String, String>();
		
		Matcher mT = tPattern.matcher(text);
		while (mT.find()){
			tnmMap.put("t", text.substring(mT.start(), mT.end()) );
			break;
		}
		
		Matcher mN = nPattern.matcher(text);
		while (mN.find()){
			tnmMap.put("n", text.substring(mN.start(), mN.end()) );
			break;
		}
		
		Matcher mM = mPattern.matcher(text);
		while (mM.find()){
			tnmMap.put("m", text.substring(mM.start(), mM.end()) );
			break;
		}		
		
		return tnmMap;
	}
	
	
	/**
	 * Returns ICD 9 values, if exist.
	 *
	 * @param patterns
	 * @param text
	 * @return
	 */
	protected Map<String, String> findICD(String text) {
		
		Map<String, String> icdMap = new HashMap<String, String>();
		
		// System.out.println("ICDText: " + text);
		
		// icd9
		String icd9 = "";
		Matcher icd9CounterMatcher = icd9CodePattern.matcher(text);
		Matcher icd9Matcher = icd9CodePattern.matcher(text);
		int icd9Count = 0;
		while (icd9CounterMatcher.find())
			icd9Count++;
		
		int icd9Num = 1;
		while (icd9Matcher.find()){			
			icd9 = icd9 + text.substring(icd9Matcher.start(), icd9Matcher.end());			
			if(icd9Num < icd9Count) {
				icd9 =  icd9 + "|" ;
			}
			icd9Num++;
		}
		
		if(icd9 != null && !icd9.isEmpty()) { 
			icdMap.put("icd9", icd9);
		}
		
		
		// icd10
		String icd10 = "";
		Matcher icd10CounterMatcher = icd10CodePattern.matcher(text);
		Matcher icd10Matcher = icd10CodePattern.matcher(text);
		int icd10Count = 0;
		while (icd10CounterMatcher.find())
			icd10Count++;
		
		int icd10Num = 1;
		while (icd10Matcher.find()){			
			icd10 = icd10 + text.substring(icd10Matcher.start(), icd10Matcher.end());			
			if(icd10Num < icd10Count) {
				icd10 =  icd10 + "|" ;
			}
			icd10Num++;
		}
		
		if(icd10 != null && !icd10.isEmpty()) { 
			icdMap.put("icd10", icd10);
			
			// remove the icd9 entry, if present, as this could be part of the icd10 value
			/*if(icdMap.containsKey("icd9")) {
				icdMap.remove("icd9");
			}*/
		}
		
		return icdMap;
	}
	
	
	/**
	 * Returns Gleason Score values, if exist.
	 *
	 * @param patterns
	 * @param text
	 * @return
	 */
	protected Map<String, String> findGleasonScore(String text) {
		
		Map<String, String> gsMap = new HashMap<String, String>();
		
		Matcher mGS1 = gsPattern1.matcher(text);
		while (mGS1.find()){
			gsMap.put("gs1", text.substring(mGS1.start(), mGS1.end()) );
			break;
		}
		
		Matcher mGS2 = gsPattern2.matcher(text);
		while (mGS2.find()){
			gsMap.put("gs2", text.substring(mGS2.start(), mGS2.end()) );
			break;
		}
		
		Matcher mGS3 = gsPattern3.matcher(text);
		while (mGS3.find()){
			gsMap.put("gs3", text.substring(mGS3.start(), mGS3.end()) );
			break;
		}
		
		return gsMap;
	}
	
	
	/**
	 * Returns true if the relation has gleason mentions.
	 *
	 * @param patterns
	 * @param text
	 * @return
	 */
	protected boolean isGleasonMatch(Pattern[] patterns, String text) {
		boolean hasMatch = false;
				
		for (Pattern p : patterns) {
			if (p.matcher(text).find()) {
				hasMatch = true;
				break;
			}
		}
		return hasMatch;
	}
	
	
	
	/**
	 * Returns true if the relation comes close to findings/impression.
	 *
	 * @param patterns
	 * @param text
	 * @return
	 */
	protected boolean isProximityMatch(Pattern[] patterns, String text) {
		boolean hasMatch = false;
				
		for (Pattern p : patterns) {
			if (p.matcher(text).find()) {
				hasMatch = true;
				break;
			}
		}
		return hasMatch;
	}
	
	
	
	/**
	 * Returns true if the text provided has a match in one of the patterns.
	 *
	 * @param patterns
	 * @param text
	 * @return
	 */
	protected boolean hasEfMatch(Pattern[] patterns, String text) {
		boolean hasMatch = false;
		for (Pattern p : patterns) {
			if (p.matcher(text).find()) {
				hasMatch = true;
				break;
			}
		}
		return hasMatch;
	}
	
	
	/**
	 * Returns true if the text provided has a match in one of the patterns.
	 *
	 * @param patterns
	 * @param text
	 * @return
	 */
	protected boolean hasRfMatch(Pattern[] patterns, String text) {
		boolean hasMatch = false;
		for (Pattern p : patterns) {
			if (p.matcher(text).find()) {
				hasMatch = true;
				break;
			}
		}
		return hasMatch;
	}
	

	/**
	 * Get the patterns from the regex file and stash them in the appropriate
	 * lists.
	 *
	 * @param regexFile
	 *            File from which to retrieve the patterns
	 * @throws IOException
	 *             If there is an error reading the file.
	 */
	protected void parseRegexFile(File regexFile) throws IOException {
		if (regexFile == null)
			throw new IllegalArgumentException("regexFile cannot be null");
		// List of Patterns to compile
		ArrayList<Pattern> efList = new ArrayList<Pattern>();
		ArrayList<Pattern> rfList = new ArrayList<Pattern>();
		
		int patternType = 3;		
		// Read in the lines of the regex file
		List<String> lines = IOUtils.readLines(FileUtils.openInputStream(regexFile));
		for (String line : lines) {
		      if (line.startsWith("#")) {
		        if (line.startsWith("#EFMAP"))
		          patternType = 1;
		        else if (line.startsWith("#RFMAP"))
		          patternType = 2;
		      } else if (StringUtils.isNotBlank(line)) {
		        if (patternType == 1)
		          efList.add(Pattern.compile(line, PATTERN_FLAGS));
		        else if (patternType == 2)
		          rfList.add(Pattern.compile(line, PATTERN_FLAGS));			        
		      }
		}
		// Stash each collection
		efPatterns = efList.toArray(new Pattern[efList.size()]);
		rfPatterns = rfList.toArray(new Pattern[rfList.size()]);

	}

	@Override
	public LeoTypeSystemDescription getLeoTypeSystemDescription() {
		TypeDescription relationFtsd;
		String relationParent = "gov.va.vinci.ef.types.Relation";
		relationFtsd = new TypeDescription_impl(relationParent, "", "uima.tcas.Annotation");
		
		relationFtsd.addFeature("RunDate", "", "uima.cas.String");
		
		relationFtsd.addFeature("Term", "", "uima.cas.String"); // Extracted
																// term string
		relationFtsd.addFeature("Value", "", "uima.cas.String"); // Numeric
		
		// Numeric value
		relationFtsd.addFeature("Value2", "", "uima.cas.String");
		
		// String value for ICD9
		relationFtsd.addFeature("ICD9", "", "uima.cas.String");
		
		// String value for ICD10
		relationFtsd.addFeature("ICD10", "", "uima.cas.String");
		
		// String value for TStage
		relationFtsd.addFeature("TStage", "", "uima.cas.String");
		
		// String value for NStage
		relationFtsd.addFeature("NStage", "", "uima.cas.String");
				
		// String value for MStage
		relationFtsd.addFeature("MStage", "", "uima.cas.String");
		
		// String value for Gleason Score
		relationFtsd.addFeature("GleasonScore", "", "uima.cas.String");
		
		// String value for Anatomic Site
		relationFtsd.addFeature("AnatomicSite", "", "uima.cas.String");
		
		// String value for Histologic Type
		relationFtsd.addFeature("HistologicType", "", "uima.cas.String");
		
		// String value for Histologic Grade
		relationFtsd.addFeature("HistologicGrade", "", "uima.cas.String");
		
		// String value for Margin Status
		relationFtsd.addFeature("MarginStatus", "", "uima.cas.String");
				
		// String value with units and extra modifiers
		relationFtsd.addFeature("ValueString", "", "uima.cas.String");

		// Snippet value with units and extra modifiers
		relationFtsd.addFeature("SnippetString", "", "uima.cas.String");
		
		LeoTypeSystemDescription types = new LeoTypeSystemDescription();
		try {
			types.addType(relationFtsd); // for target impepts with single
											// mapping

		} catch (Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	public String getRegexFilePath() {
		return regexFilePath;
	}

	public FlattenRelationAE setRegexFilePath(String regexFilePath) {
		this.regexFilePath = regexFilePath;
		return this;
	}

}
