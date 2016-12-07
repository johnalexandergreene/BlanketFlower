package org.fleen.blanketFlower.powerBox4WaySeamless;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.fleen.blanketFlower.bComposition.BComposition;
import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BCellGroup;
import org.fleen.blanketFlower.geom_Boxy.BShape;

public class Renderer_Test{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Renderer_Test(PBox pbox,Color[] colors){
    this.pbox=pbox;
    if(colors==null||colors.length<2)
      this.colors=createPalette(COLORCOUNT);
    else
      this.colors=colors;}
  
  public Renderer_Test(PBox pbox){
    this(pbox,null);}
  
  /*
   * ################################
   * PBOX
   * ################################
   */
  
  PBox pbox;
  
  /*
   * ################################
   * RENDER
   * ################################
   */
  
  public BufferedImage render(int cellspan){//ignore cellspan for now
    int 
      imagewidth=pbox.rsquare.getWidth(),
      imageheight=pbox.rsquare.getHeight();
    //init image
    BufferedImage image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_ARGB);
    Graphics2D g=image.createGraphics();
    g.setPaint(Color.white);
    g.fillRect(0,0,imagewidth,imageheight);
    //---TRANSLATE
    AffineTransform t=new AffineTransform();
    //flip for cartesian
    t.translate(0,imageheight);
    t.scale(1,-1);
    //center on the reference square
    t.translate(-pbox.rsquare.getX(),-pbox.rsquare.getY());
    //
    g.setTransform(t);
    //---RENDER
    //render reference square
    g.setStroke(createStroke());
    g.setPaint(Color.red);
    g.drawRect(
      pbox.rsquare.getX(),
      pbox.rsquare.getY(),
      pbox.rsquare.getWidth(),
      pbox.rsquare.getHeight());
    //render base
    g.setPaint(Color.green);
    g.drawRect(
      pbox.base.getX(),
      pbox.base.getY(),
      pbox.base.getWidth(),
      pbox.base.getHeight());
    //render stripes
    g.setPaint(new Color(255,0,255,128));
    for(Stripe stripe:pbox.stripes)
      g.fillRect(stripe.getX(),stripe.getY(),stripe.getWidth(),stripe.getHeight());
    
    
    //render cells
    BCellGroup basecells=pbox.base.getCells();
    Map<BCell,ColorIndex> cellcolorindices=getCellColorIndices(basecells);
    int cellcolorindex;
    Color cellcolor;
    BCell cell;
    Iterator<BCell> icells=cellcolorindices.keySet().iterator();
    Path2D cellpath;
    while(icells.hasNext()){
      cell=icells.next();
      cellcolorindex=cellcolorindices.get(cell).value;
      cellcolor=colors[cellcolorindex%colors.length];
      cellpath=cell.getPath2D();
      g.setPaint(cellcolor);
      g.fill(cellpath);
//      renderCell(image,cellcolor,cell,cellspan);
      }
  return image;}
  
  private void renderCell(BufferedImage image,Color color,BCell cell,int cellspan){
    int px,py,xoff=cell.x*cellspan,yoff=cell.y*cellspan;
    for(int cx=0;cx<cellspan;cx++){
      for(int cy=0;cy<cellspan;cy++){
        px=xoff+cx;
        py=yoff+cy;
        image.setRGB(px,py,color.getRGB());}}}
  
  //----------------
  //GET CELL COLOR
  //sum shape chorus indices at cell
  //% against color array
  //--------------------------------
  private Map<BCell,ColorIndex> getCellColorIndices(BCellGroup basecells){
    Map<BCell,ColorIndex> colorindices=new HashMap<BCell,ColorIndex>();
    BCellGroup cells;
    ColorIndex colorindex;
    for(Stripe stripe:pbox.stripes){
      cells=stripe.getCells();
      for(BCell cell:cells){
        if(!basecells.contains(cell))continue;
        colorindex=colorindices.get(cell);
        if(colorindex==null){
          colorindex=new ColorIndex();
          colorindices.put(cell,colorindex);}
        colorindex.value+=stripe.color;}}
    return colorindices;}
  
  //because Integer is immutable
  class ColorIndex{
    int value=0;
  }
  
  /*
   * ################################
   * COLORS
   * ################################
   */
  
  private static final int COLORCOUNT=16;
  Color[] colors;
  
  //PALETTE MOTHER
  Color[] createPalette(int colorcount){
    Set<Color> a=new HashSet<Color>();
    Color c;
    Random rnd=new Random();
    for(int i=0;i<colorcount;i++){
      c=new Color(64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16);
      a.add(c);}
    return a.toArray(new Color[a.size()]);}
  
  /*
   * ################################
   * STROKE
   * ################################
   */
  
  private float strokewidth=3f;
  
  private Stroke createStroke(){
    Stroke stroke=new BasicStroke(strokewidth,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
    return stroke;}

}
