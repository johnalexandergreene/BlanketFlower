package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.Powerbox_4way_Symmetric_Seamless_Chaos;
import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.stripeSystem.Base;
import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.stripeSystem.Stripe;
import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BCellGroup;

public class Renderer_Production implements Renderer{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Renderer_Production(Powerbox_4way_Symmetric_Seamless_Chaos pbox){
    this.pbox=pbox;}
  
  /*
   * ################################
   * PBOX
   * ################################
   */
  
  Powerbox_4way_Symmetric_Seamless_Chaos pbox;
  
  /*
   * ################################
   * RENDER
   * ################################
   */
  
  public BufferedImage render(int cellspan){
    Base base=pbox.stripesystem.getBase();
    int 
      imagewidth=base.getWidth()*cellspan,
      imageheight=base.getHeight()*cellspan;
    //init image
    BufferedImage image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_RGB);
    Graphics2D g=image.createGraphics();
    g.setPaint(Color.white);
    g.fillRect(0,0,imagewidth,imageheight);
    //---TRANSLATE
    AffineTransform t=new AffineTransform();
    //flip for cartesian
    t.translate(0,imageheight);
    t.scale(cellspan,-cellspan);
    //
    g.setTransform(t);
    //---RENDER
    //render cells
    BCellGroup basecells=base.getCells();
    Map<BCell,ColorIndex> cellcolorindices=getCellColorIndices(basecells);
    int cellcolorindex;
    Color cellcolor;
    BCell cell;
    Iterator<BCell> icells=cellcolorindices.keySet().iterator();
    Color[] palette=pbox.getPalette();
    while(icells.hasNext()){
      cell=icells.next();
      cellcolorindex=cellcolorindices.get(cell).value;
      cellcolor=palette[cellcolorindex%palette.length];
      renderCell(image,cellcolor,cell,cellspan);}
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
    for(Stripe stripe:pbox.stripesystem.stripes){
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
    int value=0;}

}
