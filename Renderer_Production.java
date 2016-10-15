package org.fleen.blanketFlower;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.fleen.blanketFlower.gSquid.SCell;
import org.fleen.blanketFlower.gSquid.SGrid;
import org.fleen.blanketFlower.gSquid.SShape;

public class Renderer_Production{
  
  public Renderer_Production(){
    colors=createPalette(COLORCOUNT);}
  
  public BufferedImage render(Composition composition,int cellspan){
    //get various relevant metrics
    SGrid grid=composition.getGrid();
    int 
      imagewidth=grid.getWidth()*cellspan,
      imageheight=grid.getHeight()*cellspan;
    //init image
    BufferedImage image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_RGB);
    //render cells
    Map<SCell,ColorIndex> cellcolorindices=getCellColorIndices(composition);
    int cellcolorindex;
    Color cellcolor;
    SCell cell;
    Iterator<SCell> icells=grid.getCellIterator();
    while(icells.hasNext()){
      cell=icells.next();
      cellcolorindex=cellcolorindices.get(cell).value;
      cellcolor=colors[cellcolorindex%colors.length];
      renderCell(image,cellcolor,cell,cellspan);}
  return image;}
  
  
  
  private void renderCell(BufferedImage image,Color color,SCell cell,int cellspan){
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
  private Map<SCell,ColorIndex> getCellColorIndices(Composition composition){
    Map<SCell,ColorIndex> colorindices=new HashMap<SCell,ColorIndex>();
    List<SCell> cells;
    ColorIndex colorindex;
    for(SShape shape:composition.getShapes()){
      cells=shape.getCells(composition.getGrid());
      for(SCell cell:cells){
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
