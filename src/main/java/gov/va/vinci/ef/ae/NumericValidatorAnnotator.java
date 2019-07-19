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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.descriptors.LeoConfigurationParameter;
import org.apache.commons.validator.GenericValidator;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Finds the numeric values covered by the input annotation types and then passes them to an abstract method for
 * validation.
 * <p>
 * Created by thomasginter on 9/1/15.
 * updated by olga patterson on 6/14/2016
 */
public abstract class NumericValidatorAnnotator extends LeoBaseAnnotator {


  /**
   * Pattern for finding numbers in the covered text.
   */
  // protected Pattern numberPattern = Pattern.compile(".*(c|p|y|r|a|u)?pT(x|is|0|1(mic|a|b|c)?|2(a|b)?|3(a|b)?|4(a|b|c|d)?).{0,3}N(x|0|1(a|b)?|2(a|b)?|3(a|b|c)?).{0,3}M(x|0|1(a|b)?)");
  // protected Pattern numberPattern = Pattern.compile(".*(c|y|r|a|u)?pT(x|is|0|1(mic|a|b|c)?|2(a|b|c)?|3(a|b|c)?|4(a|b|c|d)?)");
	
	protected Pattern tPattern = Pattern.compile(".*(c|y|r|a|u)?p?T(x|is|0|1(mic|a|b|c)?|2(a|b|c)?|3(a|b|c)?|4(a|b|c|d)?)");
	protected Pattern nPattern = Pattern.compile(".*(c|y|r|a|u)?p?N(x|0|1(a|b)?|2(a|b|c)?|3(a|b|c)?)");
	protected Pattern mPattern = Pattern.compile(".*(c|y|r|a|u)?p?M(x|0|1(a|b|c)?)");
	
	protected Pattern asPattern = Pattern.compile("(anatomic.{1,3}site.{0,3}:?)(.*?)(histologic.{1,3}type:?)");
	protected Pattern htPattern = Pattern.compile("(histologic.{1,3}type.{0,3}:?)(.*?)(histologic.{1,3}grade.{0,3}(\\(G\\))?:?)");
	protected Pattern hgPattern = Pattern.compile("(histologic.{1,3}grade.{0,3}(\\(G\\))?:?)(.*?)(primary.{1,3}tumor.{0,3}(\\(p?T\\))?:?)");
	protected Pattern msPattern = Pattern.compile("(margin.{1,3}status.{0,3}(\\(R\\))?:?)(.*?)(additional.{1,3}prognostic.{1,3}information:?)");
	
  
  /**
   * Remove the annotation if any of the values are invalid, the default.  If false then remove only if all the values
   * are invalid.
   */
  @LeoConfigurationParameter
  protected boolean removeIfAnyInvalid = true;


  @Override
  public void annotate(JCas aJCas) throws AnalysisEngineProcessException {
    for (String inputType : inputTypes) {
      try {
        Collection<Annotation> annotations = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, inputType, false);
        for (Annotation a : annotations) {
          String covered = a.getCoveredText();
          boolean foundValid = false, hasNumber = false;
          
          // System.out.println("Covered from numeric validator annotator: " + covered);
          
          // Validate T string
          Matcher tMatches = tPattern.matcher(covered);
          foundValid = false;
          hasNumber = false;
          while (tMatches.find()) {
            String number = covered.substring(tMatches.start(), tMatches.end());
            if (GenericValidator.isDouble(number)) {
              hasNumber = true;
              if (!isValid(new Double(number))) {
                if (removeIfAnyInvalid) {
                  foundValid = false;
                  break;
                }
              } else {
                foundValid = true;
              }
            }
          }
          if (hasNumber && !foundValid)
             a.removeFromIndexes();
          
          // Validate N string
          Matcher nMatches = nPattern.matcher(covered);
          foundValid = false;
          hasNumber = false;
          while (nMatches.find()) {
            String number = covered.substring(nMatches.start(), nMatches.end());
            if (GenericValidator.isDouble(number)) {
              hasNumber = true;
              if (!isValid(new Double(number))) {
                if (removeIfAnyInvalid) {
                  foundValid = false;
                  break;
                }
              } else {
                foundValid = true;
              }
            }
          }
          if (hasNumber && !foundValid)
             a.removeFromIndexes();
          
          
          // Validate M string
          Matcher mMatches = mPattern.matcher(covered);
          foundValid = false;
          hasNumber = false;
          while (mMatches.find()) {
            String number = covered.substring(mMatches.start(), mMatches.end());
            if (GenericValidator.isDouble(number)) {
              hasNumber = true;
              if (!isValid(new Double(number))) {
                if (removeIfAnyInvalid) {
                  foundValid = false;
                  break;
                }
              } else {
                foundValid = true;
              }
            }
          }
          if (hasNumber && !foundValid)
             a.removeFromIndexes();
          
          // Validate Anatomic Site
          Matcher asMatches = asPattern.matcher(covered);
          foundValid = false;
          hasNumber = false;
          while (asMatches.find()) {
            String number = covered.substring(asMatches.start(), asMatches.end());
            if (GenericValidator.isDouble(number)) {
              hasNumber = true;
              if (!isValid(new Double(number))) {
                if (removeIfAnyInvalid) {
                  foundValid = false;
                  break;
                }
              } else {
                foundValid = true;
              }
            }
          }
          if (hasNumber && !foundValid)
             a.removeFromIndexes();
          
          // Validate Histologic Type
          Matcher htMatches = htPattern.matcher(covered);
          foundValid = false;
          hasNumber = false;
          while (htMatches.find()) {
            String number = covered.substring(htMatches.start(), htMatches.end());
            if (GenericValidator.isDouble(number)) {
              hasNumber = true;
              if (!isValid(new Double(number))) {
                if (removeIfAnyInvalid) {
                  foundValid = false;
                  break;
                }
              } else {
                foundValid = true;
              }
            }
          }
          if (hasNumber && !foundValid)
             a.removeFromIndexes();
          
          // Validate Histologic Grade
          Matcher hgMatches = hgPattern.matcher(covered);
          foundValid = false;
          hasNumber = false;
          while (hgMatches.find()) {
            String number = covered.substring(hgMatches.start(), hgMatches.end());
            if (GenericValidator.isDouble(number)) {
              hasNumber = true;
              if (!isValid(new Double(number))) {
                if (removeIfAnyInvalid) {
                  foundValid = false;
                  break;
                }
              } else {
                foundValid = true;
              }
            }
          }
          if (hasNumber && !foundValid)
             a.removeFromIndexes();
          
          
        // Validate Margin Status
          Matcher msMatches = msPattern.matcher(covered);
          foundValid = false;
          hasNumber = false;
          while (msMatches.find()) {
            String number = covered.substring(msMatches.start(), msMatches.end());
            if (GenericValidator.isDouble(number)) {
              hasNumber = true;
              if (!isValid(new Double(number))) {
                if (removeIfAnyInvalid) {
                  foundValid = false;
                  break;
                }
              } else {
                foundValid = true;
              }
            }
          }
          if (hasNumber && !foundValid)
             a.removeFromIndexes();
          
          
          
        }
        
      } catch (CASException e) {
        throw new AnalysisEngineProcessException(e);
      }
    }
  }

  /**
   * Check the numeric value and return true if the value meets requirements.
   *
   * @param value Double value to be validated
   * @return true if number is valid
   */
  protected abstract boolean isValid(double value);

  public boolean isRemoveIfAnyInvalid() {
    return removeIfAnyInvalid;
  }

  public NumericValidatorAnnotator setRemoveIfAnyInvalid(boolean removeIfAnyInvalid) {
    this.removeIfAnyInvalid = removeIfAnyInvalid;
    return this;
  }


}
