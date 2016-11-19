package org.fleen.blanketFlower.test.contiguousCellGroupShaper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.fleen.blanketFlower.bComposition.BShape;
import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BCellGroup;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;

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
    POLYGONVERTEX=Color.gray;
  
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
        path=polygon.getPath2D();
        g.draw(path);}
      
      
    }
    
  return image;}
  
  /*
   * ################################
   * STROKE
   * ################################
   */
  
  private static final float STROKEWIDTH_DEFAULT=0.1f;
  private float strokewidth=STROKEWIDTH_DEFAULT;
  
  private Stroke createStroke(){
    Stroke stroke=new BasicStroke(strokewidth,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
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
