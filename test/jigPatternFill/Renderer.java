package org.fleen.blanketFlower.test.jigPatternFill;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BShape;
import org.fleen.util.tree.TreeNode;

public class Renderer{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Renderer(Test_JigPatternFill test){
    this.test=test;}
  
  /*
   * ################################
   * CGP
   * ################################
   */
  
  Test_JigPatternFill test;
  
  /*
   * ################################
   * RENDER
   * ################################
   */
  
  private static final int 
    SCALE=10,
    PADDING=40;
  
  private static final float 
    STROKETHICKNESS_ROOT=6.0f,
    STROKETHICKNESS_PATTERN=2.0f;
  
  private static final Color
    BACKGROUNDCOLOR=Color.gray,
    COLOR_ROOTSTROKE=new Color(255,0,0,128),
    COLOR_ROOTFILL=new Color(128,128,255,128),
    COLOR_PATTERNFILLFILL=new Color(0,0,0,128),
    COLOR_PATTERNFILLSTROKE=new Color(255,255,255,128);
  
  public BufferedImage render(){
    BPolygon rootpolygon=test.getRootPolygon();
    int 
      imagewidth=rootpolygon.getWidth()*SCALE+PADDING*2,
      imageheight=rootpolygon.getHeight()*SCALE+PADDING*2;
    //init image
    BufferedImage image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_ARGB);
    Graphics2D g=image.createGraphics();
    g.setPaint(BACKGROUNDCOLOR);
    g.fillRect(0,0,imagewidth,imageheight);
    AffineTransform t=new AffineTransform();
    t.translate(0,imageheight);
    t.translate(PADDING,-PADDING);
    t.scale(SCALE,-SCALE);
    g.setTransform(t);
    //render root polygon
    Path2D path=rootpolygon.getPath2D();
    g.setPaint(COLOR_ROOTFILL);
    g.fill(path);
    g.setPaint(COLOR_ROOTSTROKE);
    g.setStroke(createStroke(STROKETHICKNESS_ROOT));
    g.draw(path);
    //render pattern shapes
    BShape shape;
    g.setStroke(createStroke(STROKETHICKNESS_PATTERN));
    for(TreeNode n:rootpolygon.getChildren()){
      shape=(BShape)n;
      path=shape.getPath2D();
      g.setPaint(COLOR_PATTERNFILLFILL);
      g.fill(path);
      g.setPaint(COLOR_PATTERNFILLSTROKE);
      g.draw(path);}
  return image;}
  
  /*
   * ################################
   * STROKE
   * ################################
   */
  
  private Stroke createStroke(float strokewidth){
    Stroke stroke=new BasicStroke(strokewidth/SCALE,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
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
