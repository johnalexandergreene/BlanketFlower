package org.fleen.blanketFlower.jig.patternFill;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.fleen.blanketFlower.geom_Boxy.BCell;

/*
 * a 2d boolean array that we translate into cells
 *   a 1 bit bitmap, basically
 * static access to our local collection of patterns 
 */
public class Pattern{
  
  public Pattern(boolean[][] bitmap){
    this.bitmap=bitmap;}
  
  /*
   * a 1 bit image
   */
  public Pattern(BufferedImage image){
    init(image);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  private boolean[][] bitmap;
  
  /*
   * get cells corrosponding to the bitmap TRUE values
   * translate all cells by offsets
   */
  public List<BCell> getCells(int xoff,int yoff){
    int w=bitmap.length,h=bitmap[0].length;
    List<BCell> cells=new ArrayList<BCell>(w*h);
    for(int x=0;x<w;x++)
      for(int y=0;y<h;y++)
        if(bitmap[x][y])
          cells.add(new BCell(x+xoff,y+yoff));
    return cells;}
  
  /*
   * ################################
   * INIT WITH IMAGE
   * a black and white image
   * black is the cells, the TRUE
   * white is the background
   * ################################
   */
  
  private void init(BufferedImage image){
    int 
      w=image.getWidth(),
      h=image.getHeight(),
      rgbblack=Color.black.getRGB();
    bitmap=new boolean[w][h];
    for(int x=0;x<w;x++)
      for(int y=0;y<h;y++)
        bitmap[x][y]=(image.getRGB(x,y)==rgbblack);}
  
  /*
   * ################################
   * STATIC PATTERNS ACCESS
   * init, get, random get, etc
   * ################################
   */
  
  private static Pattern[] PATTERNS=null;
  
  public static final int getPatternCount(){
    Pattern[] p=getPatterns();
    return p.length;}
  
  public static final Pattern getPattern(int i){
    return PATTERNS[i];}
  
  public static final Pattern[] getPatterns(){
    if(PATTERNS==null)initPatterns();
    return PATTERNS;}
  
  private static final void initPatterns(){
    List<BufferedImage> images=new ArrayList<BufferedImage>();
    try{
      images.add(ImageIO.read(Pattern.class.getResource("p00.png")));
      images.add(ImageIO.read(Pattern.class.getResource("p01.png")));
      images.add(ImageIO.read(Pattern.class.getResource("p02.png")));
      images.add(ImageIO.read(Pattern.class.getResource("p03.png")));
      images.add(ImageIO.read(Pattern.class.getResource("p04.png")));
      images.add(ImageIO.read(Pattern.class.getResource("p05.png")));
      images.add(ImageIO.read(Pattern.class.getResource("p06.png")));
      images.add(ImageIO.read(Pattern.class.getResource("p07.png")));
      images.add(ImageIO.read(Pattern.class.getResource("p08.png")));
      images.add(ImageIO.read(Pattern.class.getResource("p09.png")));
    }catch(Exception x){
      x.printStackTrace();}
    int s=images.size();
    PATTERNS=new Pattern[s];
    for(int i=0;i<s;i++)
      PATTERNS[i]=new Pattern(images.get(i));}

}
