package org.fleen.blanketFlower.gSquid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * It's a list of chorus indices
 * Identifies a squarzy composition shape in a uniqueish contexty way.
 * The shape's chorus index, the chorus index of its parent, grandparent... root
 */
public class SShapeSignature implements Serializable{
  
  private static final long serialVersionUID=2037581696842008653L;

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SShapeSignature(SShape p){
    SShape s=p;
    while(s!=null){
      chorusindices.add(s.getChorusIndex());
      s=(SShape)s.getParent();}
    ((ArrayList<Integer>)chorusindices).trimToSize();}
  
  /*
   * ################################
   * CHORUS INDICES
   * ################################
   */
  
  private List<Integer> chorusindices=new ArrayList<Integer>();
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  //hash code is summation of chorus indices
  public Integer hashcode=null;
  
  public int hashCode(){
    if(hashcode==null)initHashCode();
    return hashcode;}
  
  //sum of component chorus indices
  private void initHashCode(){
    hashcode=new Integer(0);
    for(int a:chorusindices)
      hashcode+=a;}
  
  public boolean equals(Object a){
    SShapeSignature s0=(SShapeSignature)a;
    if(s0.hashCode()!=hashCode())return false;
    int 
      c0=s0.chorusindices.size(),
      c1=chorusindices.size();
    if(c0!=c1)return false;
    int g0,g1;
    for(int i=0;i<c0;i++){
      g0=s0.chorusindices.get(i);
      g1=chorusindices.get(i);
      if(g0!=g1)return false;}
    return true;}
  
  private String objectstring=null;
  
  public String toString(){
    if(objectstring==null)initObjectString();
    return objectstring;}
  
  private void initObjectString(){
    objectstring="Signature[";
    int s=chorusindices.size()-1;
    for(int i=0;i<s;i++)
      objectstring+=chorusindices.get(i)+",";
    objectstring+=chorusindices.get(s)+"]";}
  
}
