package org.fleen.blanketFlower.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
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

public class Renderer_Production{

  public Renderer_Production(Color[] colors){
    if(colors==null||colors.length<2)
      this.colors=createPalette(COLORCOUNT);
    else
      this.colors=colors;}
  
  public Renderer_Production(){
    this(null);}
  
  private Set<BCell> rootcells;
  
  public BufferedImage render(BComposition composition,int cellspan){
    //we clip gleaned shape cell masses against this 
    rootcells=new HashSet<BCell>(composition.getRoot().getCells());
    //get various relevant metrics
    int 
      imagewidth=composition.getRoot().getWidth()*cellspan,
      imageheight=composition.getRoot().getHeight()*cellspan;
    //init image
    BufferedImage image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_RGB);
    Graphics2D g=image.createGraphics();
    g.setPaint(Color.white);
    g.fillRect(0,0,imagewidth,imageheight);
    AffineTransform t=new AffineTransform();
    t.translate(0,imageheight);
    t.scale(cellspan,-cellspan);
    g.setTransform(t);
    //render cells
    Map<BCell,ColorIndex> cellcolorindices=getCellColorIndices(composition);
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
  private Map<BCell,ColorIndex> getCellColorIndices(BComposition composition){
    Map<BCell,ColorIndex> colorindices=new HashMap<BCell,ColorIndex>();
    BCellGroup cells;
    ColorIndex colorindex;
    for(BShape shape:composition.getShapes()){
      cells=shape.getCells();
      for(BCell cell:cells){
        if(!rootcells.contains(cell))continue;
        colorindex=colorindices.get(cell);
        if(colorindex==null){
          colorindex=new ColorIndex();
          colorindices.put(cell,colorindex);}
        colorindex.value+=shape.getChorusIndex();}}
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

}
