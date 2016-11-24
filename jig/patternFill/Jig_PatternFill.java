package org.fleen.blanketFlower.jig.patternFill;

import java.util.List;
import java.util.Random;

import org.fleen.blanketFlower.geom_Boxy.BCellGroup;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.jig.Jig;

/*
 * convert a collection of png 1 bit images into cell patterns
 *   for each pattern, all 4 orientations, flipped, etc
 *   ---we are using pretty patterns here but this could also be a logo or something
 * 
 * params for the op
 *   a fill pattern. A 2d boolean array
 *   x and y offset from the shape's cell[0,0]
 *   
 * ALG
 *   
 * get a set of cells that is the fill of that pattern in the shape's domain : P
 *   maybe just fill the bounding box, it's fast
 *   that is to say, use the bounding box to determine where the fill-blocks stop
 *   
 * get all of the cells in the shape domain : S
 * 
 * Intersect S with P to get the fill pattern cells that fill the domain of the shape
 * get that intersection group : K
 * 
 * from K get contiguous subgroups, polygons.
 * from those polygons create shapes.
 * 
 * ---
 * 
 * THIS IS NOT AN ORIENTED JIG
 * 
 */
public class Jig_PatternFill implements Jig{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */ 
  
  public Jig_PatternFill(BShape target){
    setTarget(target);}
  
  public Jig_PatternFill(){}
  
  /*
   * ################################
   * CLONE
   * ################################
   */
  
  public Object clone(){
    return new Jig_PatternFill();}
  
  /*
   * ################################
   * TARGET
   * ################################
   */
  
  BShape target;
  
  public void setTarget(BShape target){
    this.target=target;}

  public BShape getTarget(){
    return target;}
  
  /*
   * ################################
   * PATTERN
   * we can set a pattern or we can get a pattern at random from our assets
   * on clone we preserve pattern selection
   * ################################
   */
  
  Pattern pattern=null;
  
  public void setPattern(Pattern pattern){
    this.pattern=pattern;}
  
  public Pattern getPattern(){
    if(pattern==null)initRandomPattern();
    return pattern;}
  
  private void initRandomPattern(){
    Random rnd=new Random();
    int a=Pattern.getPatternCount();
    pattern=Pattern.getPattern(rnd.nextInt(a));}
  
  /*
   * ################################
   * EXECUTE
   * get our bounds
   * get offsets at origin etc
   * create cell list : K
   * add to list from pattern, getting cells over bounding box, at all offsets
   * get intersection of K and target cells: now we have a cellgroup : P
   * create shapes from P.
   * stick em in the target
   * 
   * TODO movement? orientation?
   * 
   * ################################
   */
  
  List<BShape> shapes=null;
  
  public void execute(){
    clearShapes();
    createShapes();}
  
  private void createShapes(){
    Pattern pattern=getPattern();
    //get some metrics
    int[] tbounds=target.getBounds();
    int 
      originoffsetx=tbounds[3],
      originoffsety=tbounds[2],
      xoffsetlimit=tbounds[1],
      yoffsetlimit=tbounds[0];
    //get the group of glyph cells
    BCellGroup glyphs=new BCellGroup();
    int //offset increments
      ix=pattern.getWidth(),
      iy=pattern.getHeight();
    for(int x=originoffsetx;x<=xoffsetlimit+ix;x+=ix)
      for(int y=originoffsety;y<=yoffsetlimit+iy;y+=iy)
        glyphs.addAll(pattern.getCells(x,y));
    //crop the group of glyph cells to the target area
    BCellGroup tcells=target.getCells();
    glyphs.retainAll(tcells);
    //create and install shapes
    //we should be getting 1..n polygons and 1 yard, excluding degenerate cases
    shapes=glyphs.getShapes();
    for(BShape shape:shapes){
      shape.setChorusIndex(1);
      shape.setColorIndex(1);//TODO a blinky thing
      shape.setParent(target);
      target.addChild(shape);}}
  
  private void clearShapes(){
    if(shapes==null)return;
    for(BShape shape:shapes){
      shape.setParent(null);
      target.removeChild(shape);}
    shapes.clear();}

}
