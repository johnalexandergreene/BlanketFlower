package org.fleen.blanketFlower.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.fleen.blanketFlower.bComposition.BComposition;
import org.fleen.blanketFlower.bComposition.BShape;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.blanketFlower.geom_Boxy.BYard;
import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.grid.Grid;
import org.fleen.util.tree.TreeNodeIterator;

public class Renderer_Test{
  
  private static final Color 
    BACKGROUND=new Color(45,45,45),
    CELLBASE=new Color(255,255,0,64),
    COLORSHAPESTROKE=new Color(255,255,255,128);
  
  private static final double WIDTHSHAPESTROKE=4.0;
  
  public Renderer_Test(){
    colors=createPalette(COLORCOUNT);}
  
  public BufferedImage render(BComposition composition,int cellspan){
    //get various relevant metrics
    Grid grid=composition.getGrid();
    int 
      gridwidth=grid.getWidth(),
      gridheight=grid.getHeight(),
      imagewidth=grid.getWidth()*cellspan,
      imageheight=grid.getHeight()*cellspan;
    //init image
    BufferedImage image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics=image.createGraphics();
    //fill background
    graphics.setPaint(BACKGROUND);
    graphics.fillRect(1,1,imagewidth,imageheight);
    //render untouched cells
    graphics.setPaint(CELLBASE);
    Iterator<BCell> icells=grid.getCellIterator();
    BCell cell;
    while(icells.hasNext()){
      cell=icells.next();
      graphics.fillRect(cell.x*cellspan+1,cell.y*cellspan+1,cellspan-2,cellspan-2);}
    //render shape edges
    TreeNodeIterator ishapes=composition.getShapeIterator();
    BShape shape;
    Path2D path;
    graphics.setPaint(COLORSHAPESTROKE);
    graphics.setStroke(createStroke(WIDTHSHAPESTROKE));
    while(ishapes.hasNext()){
      shape=(BShape)ishapes.next();
      if(!shape.isRoot()){
        path=getPath(shape,cellspan);
        graphics.draw(path);}}
    //render cells
    Map<BCell,ColorIndex> cellcolorindices=getCellColorIndices(composition);
    int cellcolorindex;
    Color cellcolor;
    icells=grid.getCellIterator();
    while(icells.hasNext()){
      cell=icells.next();
      cellcolorindex=cellcolorindices.get(cell).value;
      cellcolor=colors[cellcolorindex%colors.length];
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
  private Map<BCell,ColorIndex> getCellColorIndices(BComposition composition){
    Map<BCell,ColorIndex> colorindices=new HashMap<BCell,ColorIndex>();
    List<BCell> cells;
    ColorIndex colorindex;
    for(BShape shape:composition.getShapes()){
      cells=shape.getCells(composition.getGrid());
      for(BCell cell:cells){
        colorindex=colorindices.get(cell);
        if(colorindex==null){
          colorindex=new ColorIndex();
          colorindices.put(cell,colorindex);}
        colorindex.value+=shape.getChorusIndex();}}
    return colorindices;}
  
  class ColorIndex{
    int value=0;
  }
  
  
  
  
  private Stroke createStroke(double strokewidth){
    Stroke stroke=new BasicStroke((float)strokewidth,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
    return stroke;}
  
  Path2D getPath(BShape shape,int cellspan){
    if(shape instanceof BPolygon)
      return getPathForPolygon((BPolygon)shape,cellspan);
    else
      return getPathForYard((BYard)shape,cellspan);}
  
  private Path2D getPathForPolygon(BPolygon polygon,int cellspan){
    Path2D path=new Path2D.Double();
    BVertex v=polygon.vertices.get(0);
    path.moveTo(v.x*cellspan,v.y*cellspan);
    for(int i=1;i<polygon.vertices.size();i++){
      v=polygon.vertices.get(i);
      path.lineTo(v.x*cellspan,v.y*cellspan);}
    path.closePath();
    return path;}
  
  private Path2D getPathForYard(BYard yard,int cellspan){
    Path2D path=new Path2D.Double();
    for(BPolygon p:yard.polygons){
      path.append(getPathForPolygon(p,cellspan),false);}
    return path;}
  
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
