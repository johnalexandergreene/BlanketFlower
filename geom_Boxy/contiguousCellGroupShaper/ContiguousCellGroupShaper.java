package org.fleen.blanketFlower.geom_Boxy.contiguousCellGroupShaper;

import java.util.ArrayList;
import java.util.List;

import org.fleen.blanketFlower.bComposition.BShape;
import org.fleen.blanketFlower.geom_Boxy.BCell;
import org.fleen.blanketFlower.geom_Boxy.BCellGroup;
import org.fleen.blanketFlower.geom_Boxy.BPolygon;
import org.fleen.blanketFlower.geom_Boxy.BVertex;
import org.fleen.blanketFlower.geom_Boxy.BYard;

/*
 * convert a contiguous group of BCells into a BShape. 
 * Either a BPolygon or a BYard. 
 * We do it by tracing the edges of the group.
 * 
 * given contiguous cell group (we're assuming, we aren't gonna test it)
 * get the shape described by its edge/s
 * 
 * get all edge cells
 * for each edge cell : c0 
 *   get 1 or 2 edge sections. add to sections list
 * assemble sections into polygons via end-vertex matching
 * 
 * edge sections are clockwise sections of a cell edge
 * our product polygons will be clockwise
 * 
 */
public class ContiguousCellGroupShaper{
  
  /*
   * TODO this could be optimized
   * we're checking edge cell neighbors twice. Maybe that's bad.
   */
  public BShape getShape(BCellGroup contiguous){
    //get the sections
    List<EdgeSection> sectionpool=getEdgeSections(contiguous);
    System.out.println("edge section count = "+sectionpool.size());
    //assemble them into loops
    List<EdgeSectionLoop> loops=getLoops(sectionpool);
    System.out.println("loop count = "+loops.size());
    //get the polygons, then the shape
    List<BPolygon> polygons=getPolygons(loops);
    System.out.println("polygon count = "+polygons.size());
    BShape shape=getShape(polygons);
    //
    return shape;}
  
  /*
   * ################################
   * CONVERT POLYGONS TO SHAPES
   * ################################
   */
  
  /*
   * given 1..n polygons
   * if we have just 1 polygon then that's our shape
   * 
   */
  private BShape getShape(List<BPolygon> polygons){
    if(polygons.size()==1){
      return polygons.get(0);
    }else{
      BPolygon outer=getOuter(polygons);
      BYard yard=new BYard(outer);
      polygons.remove(outer);
      yard.polygons.addAll(polygons);
      return yard;}}
  
  /*
   * find the polygon that contains at least 1 vertex from each of the other polygons
   */
  private static final BPolygon getOuter(List<BPolygon> polygons){
    boolean contains;
    for(BPolygon p0:polygons){
      contains=true;
      seek:for(BPolygon p1:polygons){
        if(!p0.contains(p1.vertices.get(0))){
          contains=false;
          break seek;}}
      if(contains)return p0;}
    throw new IllegalArgumentException("wha");}
  
  /*
   * ################################
   * CONVERT EDGE SECTION LOOPS TO POLYGONS
   * ################################
   */
  
  private List<BPolygon> getPolygons(List<EdgeSectionLoop> loops){
    List<BPolygon> polygons=new ArrayList<BPolygon>(loops.size());
    for(List<EdgeSection> assembledsections:loops)
      polygons.add(getPolygon(assembledsections));
    return polygons;}
  
  private BPolygon getPolygon(List<EdgeSection> assembledsections){
    List<BVertex> vertices=new ArrayList<BVertex>();
    for(EdgeSection section:assembledsections)
      vertices.addAll(section.subList(0,section.size()-1));
    BPolygon polygon=new BPolygon(vertices);
    polygon.removeRedundantColinearVertices();//TODO
    return polygon;}
 
  /*
   * ################################
   * ASSEMBLE EDGE SECTION LOOPS
   * Given a pool of edge sections
   * pick one, arbitrarily. This is the first section in a loop of sections.
   * Init our loop with the new section
   * then, until we come around to the first section again
   *   remove the section from the pool that fits onto the 
   *   last section in the loop, by matching end vertices.
   * keep doing that until the section pool is empty
   * ################################
   */
  
  private List<EdgeSectionLoop> getLoops(List<EdgeSection> sectionpool){
    List<EdgeSectionLoop> loops=new ArrayList<EdgeSectionLoop>();
    while(!sectionpool.isEmpty())
      loops.add(getLoop(sectionpool));
    return loops;}
  
  private EdgeSectionLoop getLoop(List<EdgeSection> sectionpool){
    //init the loop
    EdgeSection section=sectionpool.remove(sectionpool.size()-1);
    EdgeSectionLoop loop=new EdgeSectionLoop(section);
    //if that init section is closed then we're done. 
    //  We got the edge of a group consisting of a single cell.
    if(section.closed)return loop;
    //otherwise
    //add sections until the loop is complete
    boolean finished=false;
    while(!finished){
      section=getNextSection(loop,sectionpool);
      sectionpool.remove(section);
      loop.add(section);
      finished=loopIsFinished(loop);}
    return loop;}
  
  /*
   * if the last vertex in the last section is equal to the 
   * first vertex in the first section then the loop is finished
   */
  private static final boolean loopIsFinished(EdgeSectionLoop loop){
    EdgeSection 
      sfirst=loop.get(0),
      slast=loop.get(loop.size()-1);
    BVertex 
      sfirstvfirst=sfirst.get(0),
      slastvlast=slast.get(slast.size()-1);
    boolean loopisfinished=sfirstvfirst.equals(slastvlast);
    return loopisfinished;}
  
  /*
   * given a list of assembled sections and a pool of unassembled sections
   * find a section in the pool that connects to the last section in the assembled sections list
   * if none is found then return null
   */
  private static final EdgeSection getNextSection(EdgeSectionLoop loop,List<EdgeSection> sectionpool){
    EdgeSection lastsection=loop.get(loop.size()-1);
    BVertex lastvertex=lastsection.get(lastsection.size()-1);
    for(EdgeSection section:sectionpool){
      if(section.get(0).equals(lastvertex))
        return section;}
    return null;}
  
  /*
   * ################################
   * GET EDGE SECTIONS
   * ################################
   */
  
  private static List<EdgeSection> getEdgeSections(BCellGroup contiguous){
    List<EdgeSection> sectionpool=new ArrayList<EdgeSection>();
    for(BCell cell:contiguous)
      if(contiguous.isEdge(cell))
        sectionpool.addAll(getEdgeSections(cell,contiguous));
    return sectionpool;}
  
  //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
  //TODO check this over. something is weird
  //the e array works when neighbor==null delivers true. weird
  
  
  private static List<EdgeSection> getEdgeSections(BCell cell,BCellGroup contiguous){
    //map cell edges to boolean array
    boolean[] e={//edges nesw
      cell.getNorth(contiguous)!=null,
      cell.getEast(contiguous)!=null,
      cell.getSouth(contiguous)!=null,
      cell.getWest(contiguous)!=null};
    BPolygon p=cell.getSquare();
    BVertex[] v=p.vertices.toArray(new BVertex[p.vertices.size()]);//vertices sw nw ne se
    EdgeSection section0=null,section1=null;
    //convert boolean array to list of sections
    //test each possible permutation crudely and literally
    //TODO we could optimize this, nest the logic
    //
    //---SINGLE EDGES
    //
    //PERMUTATION 0 : north edge
    if((e[0])&&(!e[1])&&(!e[2])&&(!e[3])){
      section0=new EdgeSection(v[1],v[2]);
    //PERMUTATION 1 : east edge
    }else if((!e[0])&&(e[1])&&(!e[2])&&(!e[3])){
      section0=new EdgeSection(v[2],v[3]);
    //PERMUTATION 2 : south edge
    }else if((!e[0])&&(!e[1])&&(e[2])&&(!e[3])){
      section0=new EdgeSection(v[3],v[0]);
    //PERMUTATION 3 : west edge
    }else if((!e[0])&&(!e[1])&&(!e[2])&&(e[3])){
      section0=new EdgeSection(v[0],v[1]);
    //  
    //---CORNERS
    //  
    //PERMUTATION 4 : north and east edges
    }else if((e[0])&&(e[1])&&(!e[2])&&(!e[3])){
      section0=new EdgeSection(v[1],v[2],v[3]);
    //PERMUTATION 5 : east and south edges
    }else if((!e[0])&&(e[1])&&(e[2])&&(!e[3])){
      section0=new EdgeSection(v[2],v[3],v[0]);
    //PERMUTATION 6 : south and west edges
    }else if((!e[0])&&(!e[1])&&(e[2])&&(e[3])){
      section0=new EdgeSection(v[3],v[0],v[1]);
    //PERMUTATION 7 : west and north edges
    }else if((e[0])&&(!e[1])&&(!e[2])&&(e[3])){
      section0=new EdgeSection(v[0],v[1],v[2]);
    //
    //---TOES
    //
    //PERMUTATION 8 : north, east and south edges
    }else if((e[0])&&(e[1])&&(e[2])&&(!e[3])){
      section0=new EdgeSection(v[1],v[2],v[3],v[0]);
    //PERMUTATION 9 : east, south and west edges
    }else if((!e[0])&&(e[1])&&(e[2])&&(e[3])){
      section0=new EdgeSection(v[2],v[3],v[0],v[1]);
    //PERMUTATION 10 : south, west and north edges
    }else if((e[0])&&(!e[1])&&(e[2])&&(e[3])){
      section0=new EdgeSection(v[3],v[0],v[1],v[2]);
    //PERMUTATION 11 : west, north and east edges
    }else if((e[0])&&(e[1])&&(!e[2])&&(e[3])){
      section0=new EdgeSection(v[0],v[1],v[2],v[3]);
    //
    //---2 PARALLEL SIDES
    //  
    //PERMUTATION 12 : north and south edges
    }else if((e[0])&&(!e[1])&&(e[2])&&(!e[3])){
      section0=new EdgeSection(v[1],v[2]);
      section1=new EdgeSection(v[3],v[0]);
    //PERMUTATION 13 : east and west edges
    }else if((!e[0])&&(e[1])&&(!e[2])&&(e[3])){
      section0=new EdgeSection(v[2],v[3]);
      section1=new EdgeSection(v[0],v[1]);
    //
    //---ALL 4. A contiguous cell group of 1
    //
    //PERMUTATION 14 : north, east, south and west edges.
    }else if((e[0])&&(e[1])&&(e[2])&&(e[3])){
      section0=new EdgeSection(true,v[0],v[1],v[2],v[3]);
      section0.closed=true;
    //WHATEVER
    }else{
      throw new IllegalArgumentException("whatever");}
    //
    List<EdgeSection> sections=new ArrayList<EdgeSection>(2);
    if(section0!=null)
      sections.add(section0);
    if(section1!=null)
      sections.add(section1);
    return sections;}
  
}
