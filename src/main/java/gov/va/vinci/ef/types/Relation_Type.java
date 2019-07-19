
/* First created by JCasGen Tue Jun 28 15:42:22 CDT 2016 */
package gov.va.vinci.ef.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;

import java.util.Date;

import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Tue Jun 28 15:42:22 CDT 2016
 * @generated */
public class Relation_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Relation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Relation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Relation(addr, Relation_Type.this);
  			   Relation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Relation(addr, Relation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Relation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("gov.va.vinci.ef.types.Relation");
 
  /** @generated */
  final Feature casFeat_RunDate;
  /** @generated */
  final int     casFeatCode_RunDate;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRunDate(int addr) {
        if (featOkTst && casFeat_RunDate == null)
      jcas.throwFeatMissing("RunDate", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_RunDate);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRunDate(int addr, String v) {
        if (featOkTst && casFeat_RunDate == null)
      jcas.throwFeatMissing("RunDate", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_RunDate, v);}
   
  /** @generated */
  final Feature casFeat_Term;
  /** @generated */
  final int     casFeatCode_Term;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTerm(int addr) {
        if (featOkTst && casFeat_Term == null)
      jcas.throwFeatMissing("Term", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Term);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTerm(int addr, String v) {
        if (featOkTst && casFeat_Term == null)
      jcas.throwFeatMissing("Term", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Term, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Value;
  /** @generated */
  final int     casFeatCode_Value;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getValue(int addr) {
        if (featOkTst && casFeat_Value == null)
      jcas.throwFeatMissing("Value", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Value);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setValue(int addr, String v) {
        if (featOkTst && casFeat_Value == null)
      jcas.throwFeatMissing("Value", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Value, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Value2;
  /** @generated */
  final int     casFeatCode_Value2;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getValue2(int addr) {
        if (featOkTst && casFeat_Value2 == null)
      jcas.throwFeatMissing("Value2", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Value2);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setValue2(int addr, String v) {
        if (featOkTst && casFeat_Value2 == null)
      jcas.throwFeatMissing("Value2", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Value2, v);}
  
  
  /** @generated */
  final Feature casFeat_ICD9;
  /** @generated */
  final int     casFeatCode_ICD9;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getICD9(int addr) {
        if (featOkTst && casFeat_ICD9 == null)
      jcas.throwFeatMissing("ICD9", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ICD9);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setICD9(int addr, String v) {
        if (featOkTst && casFeat_ICD9 == null)
      jcas.throwFeatMissing("ICD9", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_ICD9, v);}
  
  
  
  /** @generated */
  final Feature casFeat_ICD10;
  /** @generated */
  final int     casFeatCode_ICD10;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getICD10(int addr) {
        if (featOkTst && casFeat_ICD10 == null)
      jcas.throwFeatMissing("ICD10", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ICD10);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setICD10(int addr, String v) {
        if (featOkTst && casFeat_ICD10 == null)
      jcas.throwFeatMissing("ICD10", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_ICD10, v);}
  
  
  /** @generated */
  final Feature casFeat_TStage;
  /** @generated */
  final int     casFeatCode_TStage;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTStage(int addr) {
        if (featOkTst && casFeat_TStage == null)
      jcas.throwFeatMissing("TStage", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_TStage);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTStage(int addr, String v) {
        if (featOkTst && casFeat_TStage == null)
      jcas.throwFeatMissing("TStage", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_TStage, v);}
   
  
  /** @generated */
  final Feature casFeat_NStage;
  /** @generated */
  final int     casFeatCode_NStage;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getNStage(int addr) {
        if (featOkTst && casFeat_NStage == null)
      jcas.throwFeatMissing("NStage", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_NStage);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNStage(int addr, String v) {
        if (featOkTst && casFeat_NStage == null)
      jcas.throwFeatMissing("NStage", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_NStage, v);}
  
  
  /** @generated */
  final Feature casFeat_MStage;
  /** @generated */
  final int     casFeatCode_MStage;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMStage(int addr) {
        if (featOkTst && casFeat_MStage == null)
      jcas.throwFeatMissing("MStage", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_MStage);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMStage(int addr, String v) {
        if (featOkTst && casFeat_MStage == null)
      jcas.throwFeatMissing("MStage", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_MStage, v);}
  
  
  /** @generated */
  final Feature casFeat_GleasonScore;
  /** @generated */
  final int     casFeatCode_GleasonScore;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getGleasonScore(int addr) {
        if (featOkTst && casFeat_GleasonScore == null)
      jcas.throwFeatMissing("GleasonScore", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_GleasonScore);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGleasonScore(int addr, String v) {
        if (featOkTst && casFeat_GleasonScore == null)
      jcas.throwFeatMissing("GleasonScore", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_GleasonScore, v);}
  
  
  /** @generated */
  final Feature casFeat_AnatomicSite;
  /** @generated */
  final int     casFeatCode_AnatomicSite;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAnatomicSite(int addr) {
        if (featOkTst && casFeat_AnatomicSite == null)
      jcas.throwFeatMissing("AnatomicSite", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_AnatomicSite);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnatomicSite(int addr, String v) {
        if (featOkTst && casFeat_AnatomicSite == null)
      jcas.throwFeatMissing("AnatomicSite", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_AnatomicSite, v);}
  
   
  /** @generated */
  final Feature casFeat_HistologicType;
  /** @generated */
  final int     casFeatCode_HistologicType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getHistologicType(int addr) {
        if (featOkTst && casFeat_HistologicType == null)
      jcas.throwFeatMissing("HistologicType", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_HistologicType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHistologicType(int addr, String v) {
        if (featOkTst && casFeat_HistologicType == null)
      jcas.throwFeatMissing("HistologicType", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_HistologicType, v);}
  
  
  /** @generated */
  final Feature casFeat_HistologicGrade;
  /** @generated */
  final int     casFeatCode_HistologicGrade;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getHistologicGrade(int addr) {
        if (featOkTst && casFeat_HistologicGrade == null)
      jcas.throwFeatMissing("HistologicGrade", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_HistologicGrade);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHistologicGrade(int addr, String v) {
        if (featOkTst && casFeat_HistologicGrade == null)
      jcas.throwFeatMissing("HistologicGrade", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_HistologicGrade, v);}
  
  
  /** @generated */
  final Feature casFeat_MarginStatus;
  /** @generated */
  final int     casFeatCode_MarginStatus;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMarginStatus(int addr) {
        if (featOkTst && casFeat_MarginStatus == null)
      jcas.throwFeatMissing("MarginStatus", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_MarginStatus);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMarginStatus(int addr, String v) {
        if (featOkTst && casFeat_MarginStatus == null)
      jcas.throwFeatMissing("MarginStatus", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_MarginStatus, v);}
  
  
  /** @generated */
  final Feature casFeat_ValueString;
  /** @generated */
  final int     casFeatCode_ValueString;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getValueString(int addr) {
        if (featOkTst && casFeat_ValueString == null)
      jcas.throwFeatMissing("ValueString", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ValueString);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setValueString(int addr, String v) {
        if (featOkTst && casFeat_ValueString == null)
      jcas.throwFeatMissing("ValueString", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_ValueString, v);}
  
  
  
  /** @generated */
  final Feature casFeat_SnippetString;
  /** @generated */
  final int     casFeatCode_SnippetString;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSnippetString(int addr) {
        if (featOkTst && casFeat_SnippetString == null)
      jcas.throwFeatMissing("SnippetString", "gov.va.vinci.ef.types.Relation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_SnippetString);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSnippetString(int addr, String v) {
        if (featOkTst && casFeat_SnippetString == null)
      jcas.throwFeatMissing("SnippetString", "gov.va.vinci.ef.types.Relation");
    ll_cas.ll_setStringValue(addr, casFeatCode_SnippetString, v);}
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Relation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());
    
    casFeat_RunDate = jcas.getRequiredFeatureDE(casType, "RunDate", "uima.cas.String", featOkTst);
    casFeatCode_RunDate  = (null == casFeat_RunDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_RunDate).getCode();
 
    casFeat_Term = jcas.getRequiredFeatureDE(casType, "Term", "uima.cas.String", featOkTst);
    casFeatCode_Term  = (null == casFeat_Term) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Term).getCode();
 
    casFeat_Value = jcas.getRequiredFeatureDE(casType, "Value", "uima.cas.String", featOkTst);
    casFeatCode_Value  = (null == casFeat_Value) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Value).getCode();

    casFeat_Value2 = jcas.getRequiredFeatureDE(casType, "Value2", "uima.cas.String", featOkTst);
    casFeatCode_Value2  = (null == casFeat_Value2) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Value2).getCode();
    
    casFeat_ICD9 = jcas.getRequiredFeatureDE(casType, "ICD9", "uima.cas.String", featOkTst);
    casFeatCode_ICD9  = (null == casFeat_ICD9) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ICD9).getCode();
    
    casFeat_ICD10 = jcas.getRequiredFeatureDE(casType, "ICD10", "uima.cas.String", featOkTst);
    casFeatCode_ICD10  = (null == casFeat_ICD10) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ICD10).getCode();
 
    casFeat_TStage = jcas.getRequiredFeatureDE(casType, "TStage", "uima.cas.String", featOkTst);
    casFeatCode_TStage  = (null == casFeat_TStage) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_TStage).getCode();
    
    casFeat_NStage = jcas.getRequiredFeatureDE(casType, "NStage", "uima.cas.String", featOkTst);
    casFeatCode_NStage  = (null == casFeat_NStage) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_NStage).getCode();
    
    casFeat_MStage = jcas.getRequiredFeatureDE(casType, "MStage", "uima.cas.String", featOkTst);
    casFeatCode_MStage  = (null == casFeat_MStage) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_MStage).getCode();
    
    casFeat_GleasonScore = jcas.getRequiredFeatureDE(casType, "GleasonScore", "uima.cas.String", featOkTst);
    casFeatCode_GleasonScore  = (null == casFeat_GleasonScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_GleasonScore).getCode();
    
    casFeat_AnatomicSite = jcas.getRequiredFeatureDE(casType, "AnatomicSite", "uima.cas.String", featOkTst);
    casFeatCode_AnatomicSite  = (null == casFeat_AnatomicSite) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_AnatomicSite).getCode();
    
    casFeat_HistologicType = jcas.getRequiredFeatureDE(casType, "HistologicType", "uima.cas.String", featOkTst);
    casFeatCode_HistologicType  = (null == casFeat_HistologicType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_HistologicType).getCode();
    
    casFeat_HistologicGrade = jcas.getRequiredFeatureDE(casType, "HistologicGrade", "uima.cas.String", featOkTst);
    casFeatCode_HistologicGrade  = (null == casFeat_HistologicGrade) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_HistologicGrade).getCode();
    
    casFeat_MarginStatus = jcas.getRequiredFeatureDE(casType, "MarginStatus", "uima.cas.String", featOkTst);
    casFeatCode_MarginStatus  = (null == casFeat_MarginStatus) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_MarginStatus).getCode();
 
    casFeat_ValueString = jcas.getRequiredFeatureDE(casType, "ValueString", "uima.cas.String", featOkTst);
    casFeatCode_ValueString  = (null == casFeat_ValueString) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ValueString).getCode();
    
    casFeat_SnippetString = jcas.getRequiredFeatureDE(casType, "SnippetString", "uima.cas.String", featOkTst);
    casFeatCode_SnippetString  = (null == casFeat_SnippetString) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_SnippetString).getCode();

  }
}



    