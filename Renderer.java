package org.fleen.squarzy;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Renderer{
  
  public Renderer(){
    initColors();}
  
  public BufferedImage render(Grid grid,int cellspan){
    int 
      gridwidth=grid.getWidth(),
      gridheight=grid.getHeight(),
      imagewidth=grid.getWidth()*cellspan,
      imageheight=grid.getHeight()*cellspan;
    
    BufferedImage image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_RGB);
    Color cellcolor;
    Cell cell;
    for(int x=0;x<gridwidth;x++){
      for(int y=0;y<gridheight;y++){
          cell=grid.getCell(x,y);
          cellcolor=colors[cell.test%colors.length];
          renderCell(image,cellcolor,x,y,cellspan);}}
  return image;}
  
  private void renderCell(BufferedImage image,Color color,int x,int y,int cellspan){
    int px,py,xoff=x*cellspan,yoff=y*cellspan;
    for(int cx=0;cx<cellspan;cx++){
      for(int cy=0;cy<cellspan;cy++){
        px=xoff+cx;
        py=yoff+cy;
        image.setRGB(px,py,color.getRGB());}}}
  
  /*
   * ################################
   * COLORS
   * ################################
   */
  
  private static final int COLORCOUNT=6;
  
  Random rnd=new Random();
  
  Color[] colors;
  
  void initColors(){
    List<Color> a=new ArrayList<Color>();
    for(int i=0;i<COLORCOUNT;i++)
      a.add(getRandomColor());
    colors=a.toArray(new Color[a.size()]);}
  
  private Color getRandomColor(){
    Color c=new Color(64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16);
    return c;}

}
