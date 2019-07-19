

/* First created by JCasGen Tue Jun 28 15:42:22 CDT 2016 */
package gov.va.vinci.ef.types;

import java.util.Date;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Jun 28 15:42:22 CDT 2016
 * XML source: C:/Users/VHASLC~3/AppData/Local/Temp/leoTypeDescription_8508a9f0-8c28-4118-921f-f59dc97ac5a96406657150654531677.xml
 * @generated */
public class Relation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Relation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Relation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Relation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Relation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Relation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
//*--------------*
  //* Feature: RunDate

  /** getter for RunDate - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRunDate() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_RunDate == null)
      jcasType.jcas.throwFeatMissing("RunDate", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_RunDate);}
    
  /** setter for RunDate - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRunDate(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_RunDate == null)
      jcasType.jcas.throwFeatMissing("RunDate", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_RunDate, v);}
  
  
    
  //*--------------*
  //* Feature: Term

  /** getter for Term - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTerm() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_Term == null)
      jcasType.jcas.throwFeatMissing("Term", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_Term);}
    
  /** setter for Term - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTerm(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_Term == null)
      jcasType.jcas.throwFeatMissing("Term", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_Term, v);}    
   
    
  //*--------------*
  //* Feature: Value

  /** getter for Value - gets 
   * @generated
   * @return value of the feature 
   */
  public String getValue() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_Value == null)
      jcasType.jcas.throwFeatMissing("Value", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_Value);}
    
  /** setter for Value - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_Value == null)
      jcasType.jcas.throwFeatMissing("Value", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_Value, v);}    
   
    
  //*--------------*
  //* Feature: Value2

  /** getter for Value2 - gets 
   * @generated
   * @return value of the feature 
   */
  public String getValue2() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_Value2 == null)
      jcasType.jcas.throwFeatMissing("Value2", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_Value2);}
    
  /** setter for Value2 - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue2(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_Value2 == null)
      jcasType.jcas.throwFeatMissing("Value2", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_Value2, v);}    
  
  
  //*--------------*
  //* Feature: ICD9

  /** getter for ICD9 - gets 
   * @generated
   * @return value of the feature 
   */
  public String getICD9() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ICD9 == null)
      jcasType.jcas.throwFeatMissing("ICD9", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ICD9);}
    
  /** setter for ICD9 - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setICD9(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ICD9 == null)
      jcasType.jcas.throwFeatMissing("ICD9", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ICD9, v);}
  
  
//*--------------*
  //* Feature: ICD10

  /** getter for ICD10 - gets 
   * @generated
   * @return value of the feature 
   */
  public String getICD10() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ICD10 == null)
      jcasType.jcas.throwFeatMissing("ICD10", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ICD10);}
    
  /** setter for ICD10 - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setICD10(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ICD10 == null)
      jcasType.jcas.throwFeatMissing("ICD10", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ICD10, v);}
  
  
  //*--------------*
  //* Feature: TStage

  /** getter for TStage - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTStage() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_TStage == null)
      jcasType.jcas.throwFeatMissing("TStage", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_TStage);}
    
  /** setter for TStage - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTStage(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_TStage == null)
      jcasType.jcas.throwFeatMissing("TStage", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_TStage, v);}
  
  
//*--------------*
  //* Feature: NStage

  /** getter for NStage - gets 
   * @generated
   * @return value of the feature 
   */
  public String getNStage() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_NStage == null)
      jcasType.jcas.throwFeatMissing("NStage", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_NStage);}
    
  /** setter for NStage - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNStage(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_NStage == null)
      jcasType.jcas.throwFeatMissing("NStage", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_NStage, v);}

  
//*--------------*
  //* Feature: MStage

  /** getter for MStage - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMStage() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_MStage == null)
      jcasType.jcas.throwFeatMissing("MStage", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_MStage);}
    
  /** setter for MStage - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMStage(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_MStage == null)
      jcasType.jcas.throwFeatMissing("MStage", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_MStage, v);}
   
//*--------------*
  //* Feature: GleasonScore

  /** getter for GleasonScore - gets 
   * @generated
   * @return value of the feature 
   */
  public String getGleasonScore() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_GleasonScore == null)
      jcasType.jcas.throwFeatMissing("GleasonScore", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_GleasonScore);}
    
  /** setter for MStage - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGleasonScore(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_GleasonScore == null)
      jcasType.jcas.throwFeatMissing("GleasonScore", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_GleasonScore, v);}
  
  
//*--------------*
  //* Feature: Anatomic Site

  /** getter for AnatomicSite - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAnatomicSite() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_AnatomicSite == null)
      jcasType.jcas.throwFeatMissing("AnatomicSite", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_AnatomicSite);}
    
  /** setter for AnatomicSite - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnatomicSite(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_AnatomicSite == null)
      jcasType.jcas.throwFeatMissing("AnatomicSite", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_AnatomicSite, v);}
    
  
//*--------------*
  //* Feature: Histologic Type

  /** getter for HistologicType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getHistologicType() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_HistologicType == null)
      jcasType.jcas.throwFeatMissing("HistologicType", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_HistologicType);}
    
  /** setter for HistologicType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHistologicType(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_HistologicType == null)
      jcasType.jcas.throwFeatMissing("HistologicType", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_HistologicType, v);}
  
  
//*--------------*
  //* Feature: Histologic Grade

  /** getter for HistologicGrade - gets 
   * @generated
   * @return value of the feature 
   */
  public String getHistologicGrade() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_HistologicGrade == null)
      jcasType.jcas.throwFeatMissing("HistologicGrade", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_HistologicGrade);}
    
  /** setter for HistologicGrade - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHistologicGrade(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_HistologicGrade == null)
      jcasType.jcas.throwFeatMissing("HistologicGrade", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_HistologicGrade, v);}
  
   
//*--------------*
  //* Feature: Margin Status

  /** getter for MarginStatus - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMarginStatus() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_MarginStatus == null)
      jcasType.jcas.throwFeatMissing("MarginStatus", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_MarginStatus);}
    
  /** setter for MarginStatus - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMarginStatus(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_MarginStatus == null)
      jcasType.jcas.throwFeatMissing("MarginStatus", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_MarginStatus, v);}
  
   
  //*--------------*
  //* Feature: ValueString

  /** getter for ValueString - gets 
   * @generated
   * @return value of the feature 
   */
  public String getValueString() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ValueString == null)
      jcasType.jcas.throwFeatMissing("ValueString", "gov.va.vinci.ef.types.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ValueString);}
    
  /** setter for ValueString - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setValueString(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ValueString == null)
      jcasType.jcas.throwFeatMissing("ValueString", "gov.va.vinci.ef.types.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ValueString, v);}    
  

//*--------------*
//* Feature: Snippet

/** getter for SnippetString - gets 
 * @generated
 * @return value of the feature 
 */
public String getSnippetString() {
  if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_SnippetString == null)
    jcasType.jcas.throwFeatMissing("SnippetString", "gov.va.vinci.ef.types.Relation");
  return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_SnippetString);}
  
/** setter for ValueString - sets  
 * @generated
 * @param v value to set into the feature 
 */
public void setSnippetString(String v) {
  if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_SnippetString == null)
    jcasType.jcas.throwFeatMissing("SnippetString", "gov.va.vinci.ef.types.Relation");
  jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_SnippetString, v);}    
}
    