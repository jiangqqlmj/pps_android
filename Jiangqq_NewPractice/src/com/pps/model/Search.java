package com.pps.model;

public class Search {
  private String pt;
  private String tp;
/**
 * 
 */
public Search() {
	super();
}
/**
 * @param pt
 * @param tp
 */
public Search(String pt, String tp) {
	super();
	this.pt = pt;
	this.tp = tp;
}
/**
 * @return the pt
 */
public String getPt() {
	return pt;
}
/**
 * @param pt the pt to set
 */
public void setPt(String pt) {
	this.pt = pt;
}
/**
 * @return the tp
 */
public String getTp() {
	return tp;
}
/**
 * @param tp the tp to set
 */
public void setTp(String tp) {
	this.tp = tp;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "Search [pt=" + pt + ", tp=" + tp + "]";
}
  
  
}
