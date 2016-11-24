package org.fleen.blanketFlower.test.contiguousCellGroupShaper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BCellGroup;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.blanketFlower.geom_Boxy.BVertex;

public class Renderer{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Renderer(Test_ContiguousCellGroupShaper cgp){
    this.cgp=cgp;}
  
  /*
   * ################################
   * CGP
   * ################################
   */
  
  Test_ContiguousCellGroupShaper cgp;
  
  /*
   * ################################
   * RENDER
   * ################################
   */
  
  private static final int 
    CELLSPAN=30,
    PADDING=40;
  
  private static final Color
    BACKGROUNDCOLOR=Color.white,
    CELLCOLOR=Color.red,
    POLYGONSTROKECOLOR=Color.black,
    DOTCOLOR=Color.black;
  
  public BufferedImage render(){
    int 
      imagewidth=cgp.cellmess.width*CELLSPAN+PADDING*2,
      imageheight=cgp.cellmess.height*CELLSPAN+PADDING*2;
    //init image
    BufferedImage image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_RGB);
    Graphics2D g=image.createGraphics();
    g.setPaint(BACKGROUNDCOLOR);
    g.fillRect(0,0,imagewidth,imageheight);
    AffineTransform t=new AffineTransform();
    t.translate(0,imageheight);
    t.translate(PADDING,-PADDING);
    t.scale(CELLSPAN,-CELLSPAN);
    g.setTransform(t);
    //render cells
    Color cellcolor=CELLCOLOR;
    Path2D path;
    for(BCell cell:cgp.cellmess){
      path=cell.getPath2D();
      g.setPaint(cellcolor);
      g.fill(path);}
    //render contiguous groups cells
    for(BCellGroup cgroup:cgp.cgroups){
      g.setPaint(getRandomColor());
      for(BCell cell:cgroup){
        path=cell.getPath2D();
        g.fill(path);}}
    //render polygons
    g.setStroke(createStroke());
    g.setPaint(POLYGONSTROKECOLOR);
    for(BShape shape:cgp.shapes){
      for(BPolygon polygon:shape.getPolygons()){
        //stroke edge
        path=polygon.getPath2D();
        g.draw(path);
        //do vertex dots
        for(BVertex v:polygon.vertices){
          doDot(v.x,v.y,g);}}}
  return image;}
  
  /*
   * ################################
   * DOT
   * ################################
   */
  
  private static final double DOTWIDTH=0.3f;
  
  private void doDot(double x,double y,Graphics2D g){
    Ellipse2D a=new Ellipse2D.Double(x-DOTWIDTH/2,y-DOTWIDTH/2,DOTWIDTH,DOTWIDTH);
    g.setPaint(DOTCOLOR);
    g.fill(a);}
  
  /*
   * ################################
   * STROKE
   * ################################
   */
  
  private static final float STROKEWIDTH=0.1f;
  
  private Stroke createStroke(){
    Stroke stroke=new BasicStroke(STROKEWIDTH,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
    return stroke;}
  
  /*
   * ################################
   * RANDOM COLOR
   * ################################
   */
  
  //PASTEL PALETTE MOTHER
  Color getRandomColor(){
    Random rnd=new Random();
    Color c=new Color(128+rnd.nextInt(8)*16,128+rnd.nextInt(8)*16,128+rnd.nextInt(8)*16);
    return c;}
  
  

}
