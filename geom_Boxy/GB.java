package org.fleen.blanketFlower.geom_Boxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fleen.blanketFlower.bComposition.BShape;
import org.fleen.geom_2D.GD;

/*
 * Boxy geometry
 * 
 * an array of square cells
 * 
 * all coordinates are integers
 * 
 * north is up, Y+ 
 * east is right, X+
 * 
 * cell group contiguity connection is via flats
 * 
 * 4 valid directions for distance and direction checking
 * (?)
 * 
 */
public class GB{
  
  /*
   * ################################
   * DIRECTION
   * ################################
   */
  
  public static final int
    //directions
    DIR_INVALID=-2,//a diagonal
    DIR_UNDEFINED=-1,//no relevant info
    DIR_NORTH=0,
    DIR_EAST=1,
    DIR_SOUTH=2,
    DIR_WEST=3,
    //turns
    TURN_STRAIGHT=0,
    TURN_RIGHT=1,
    TURN_LEFT=-1,
    TURN_REVERSE=-2;
  
  public static final int getDirection(BVertex v0,BVertex v1){
    return getDirection(v0.x,v0.y,v1.x,v1.y);}
  
  public static final int getDirection(BCell c0,BCell c1){
    return getDirection(c0.x,c0.y,c1.x,c1.y);}
  
  public static final int getDirection(int x0,int y0,int x1,int y1){
    if(x0==x1){
      if(y0<y1){
        return DIR_NORTH;
      }else if(y0>y1){
        return DIR_SOUTH;
      }else{//x0==x1 and y0==y1
        return DIR_UNDEFINED;}
    }else if(y0==y1){
      if(x0<x1){
        return DIR_EAST;
      }else if(x0>x1){
        return DIR_WEST;
      }else{//y0==y1 and x0==x1
        return DIR_UNDEFINED;
      }
    }else{//diagonal
      return DIR_INVALID;}}
  
  /*
   * given 2 directions, get the change in direction
   * if d0==d1 then delta=0 : no change in direction
   * if we take a left turn then delta=-1
   * if we take a right turn then delta=1
   * if we reverse direction then delta=-2
   */
  public static final int getDirectionDelta(int d0,int d1){
    //normalize
    d0=d0%4;
    d1=d1%4;
    //test
    if(d0==d1)return TURN_STRAIGHT;
    if(d0==DIR_NORTH){
      if(d1==DIR_EAST){
        return TURN_RIGHT;
      }else if(d1==DIR_SOUTH){
        return TURN_REVERSE;
      }else{//d1==DIR_WEST
        return TURN_LEFT;}
    }else if(d0==DIR_EAST){
      if(d1==DIR_SOUTH){
        return TURN_RIGHT;
      }else if(d1==DIR_WEST){
        return TURN_REVERSE;
      }else{//d1==DIR_NORTH
        return TURN_LEFT;}
    }else if(d0==DIR_SOUTH){
      if(d1==DIR_WEST){
        return TURN_RIGHT;
      }else if(d1==DIR_NORTH){
        return TURN_REVERSE;
      }else{//d1==DIR_EAST
        return TURN_LEFT;}
    }else if(d0==DIR_WEST){
      if(d1==DIR_NORTH){
        return TURN_RIGHT;
      }else if(d1==DIR_EAST){
        return TURN_REVERSE;
      }else{//d1==DIR_SOUTH
        return TURN_LEFT;}}
    throw new IllegalArgumentException("foo?");}
  
  /*
   * given a direction and a delta off that direction (a turn), get the resulting direction
   */
  public static final int getDirectionAtDelta(int dir,int delta){
    if(dir==DIR_NORTH){
      if(delta==TURN_STRAIGHT){
        return DIR_NORTH;
      }else if(delta==TURN_RIGHT){
        return DIR_EAST;
      }else if(delta==TURN_REVERSE){
        return DIR_SOUTH;
      }else if(delta==TURN_LEFT){
        return DIR_WEST;
      }else{
        throw new IllegalArgumentException("invalid delta");}
    }else if(dir==DIR_EAST){
      if(delta==TURN_STRAIGHT){
        return DIR_EAST;
      }else if(delta==TURN_RIGHT){
        return DIR_SOUTH;
      }else if(delta==TURN_REVERSE){
        return DIR_WEST;
      }else if(delta==TURN_LEFT){
        return DIR_NORTH;
      }else{
        throw new IllegalArgumentException("invalid delta");}
    }else if(dir==DIR_SOUTH){
      if(delta==TURN_STRAIGHT){
        return DIR_SOUTH;
      }else if(delta==TURN_RIGHT){
        return DIR_WEST;
      }else if(delta==TURN_REVERSE){
        return DIR_NORTH;
      }else if(delta==TURN_LEFT){
        return DIR_EAST;
      }else{
        throw new IllegalArgumentException("invalid delta");}
    }else if(dir==DIR_WEST){
      if(delta==TURN_STRAIGHT){
        return DIR_WEST;
      }else if(delta==TURN_RIGHT){
        return DIR_NORTH;
      }else if(delta==TURN_REVERSE){
        return DIR_EAST;
      }else if(delta==TURN_LEFT){
        return DIR_SOUTH;
      }else{
        throw new IllegalArgumentException("invalid delta");}
    }else{
      throw new IllegalArgumentException("invalid dir");}}
  
  public static final int getOppositeDirection(int d){
    if(d==DIR_NORTH){
      return DIR_SOUTH;
    }else if(d==DIR_EAST){
      return DIR_WEST;
    }else if(d==DIR_SOUTH){
      return DIR_NORTH;
    }else if(d==DIR_WEST){
      return DIR_EAST;
    }else{
      throw new IllegalArgumentException("invalid dir");}}
  
  /*
   * ################################
   * TWIST
   * AKA CHIRALITY
   * CLOCKWISE OR COUNTERCLOCKWISE
   * Given a polygon, given the direction of traversal when addressing 
   * the vertices of that polygon in the index-positive direction
   * are we moving clockwise or counterclockwise
   * ################################
   */
  
  public static final boolean TWIST_CW=true,TWIST_CCW=false;
  
  /*
   * convert to double[][] and use GD.getSignedArea2D
   * 
   * clockwise is true, counterclockwise is false
   * gather direction deltas
   * 
   * TODO make a faster version
   */
  public static final boolean getTwist(BPolygon polygon){
    int s=polygon.vertices.size();
    double[][] d=new double[polygon.vertices.size()][2];
    BVertex v;
    for(int i=0;i<s;i++){
      v=polygon.vertices.get(i);
      d[i][0]=v.x;
      d[i][1]=v.y;}
    double b=GD.getSignedArea2D(d);
    if(b<0)
      return TWIST_CW;
    else
      return TWIST_CCW;}
  
  /*
   * ################################
   * DISTANCE
   * We measure distance our 2 axes : NS and EW
   * All other directions are invalid
   * It's a nice system. Simple.
   * ################################
   */
  
  public static final int getDistance(BVertex v0,BVertex v1){
    return getDistance(v0.x,v0.y,v1.x,v1.y);}
  
  public static final int getDistance(int x0,int y0,int x1,int y1){
    if(x0==x1)
      return Math.abs(y0-y1);
    else
      return Math.abs(x0-x1);}
  
  /*
   * ################################
   * POLYGON CELL FILL
   * Given a polygon, get the cells contained within that polygon
   * ################################
   */
  
  public static final List<BCell> getCells(BPolygon polygon){
    //get twist
    boolean twist=polygon.getTwist();
    //get headcells and tailcells
    //that's the vertical strands of cells to the left and right of open spaces
    Set<BCell> 
      heads=new HashSet<BCell>(),
      tails=new HashSet<BCell>();
    int dir;
    if(twist==GB.TWIST_CW){
      for(BSeg seg:polygon.getSegs()){
        dir=seg.getForward();
        if(dir==GB.DIR_NORTH)
          heads.addAll(getCellsOnRight(seg,GB.DIR_NORTH));
        else if(dir==GB.DIR_SOUTH)
          tails.addAll(getCellsOnRight(seg,GB.DIR_SOUTH));}
    }else{//twist==TWIST_CCW
      for(BSeg seg:polygon.getSegs()){
        dir=seg.getForward();
        if(dir==GB.DIR_SOUTH)
          heads.addAll(getCellsOnLeft(seg,GB.DIR_SOUTH));
        else if(dir==GB.DIR_NORTH)
          tails.addAll(getCellsOnLeft(seg,GB.DIR_NORTH));}}
    //fill in between heads and tails
    List<BCell> fill=new ArrayList<BCell>();
    for(BCell h:heads)
      fillRow(h,tails,fill);
    fill.addAll(tails);
    return fill;}
  
  private static final void fillRow(BCell head,Set<BCell> tails,List<BCell> fillz){
    BCell a=head;
    while(!tails.contains(a)){
      fillz.add(a);
      a=a.getEast();}}
  
  private static final List<BCell> getCellsOnRight(BSeg seg,int heading){
    List<BCell> cells=new ArrayList<BCell>();
    if(heading==GB.DIR_NORTH){
      for(int y=seg.v0.y;y<seg.v1.y;y++)
        cells.add(new BCell(seg.v0.x,y));  
    }else{//heading==DIR_SOUTH
      for(int y=seg.v0.y-1;y>seg.v1.y-1;y--)
        cells.add(new BCell(seg.v0.x-1,y));}
    return cells;}
  
  private static final List<BCell> getCellsOnLeft(BSeg seg,int heading){
    List<BCell> cells=new ArrayList<BCell>();
    if(heading==GB.DIR_NORTH){
      for(int y=seg.v0.y;y<seg.v1.y;y++)
        cells.add(new BCell(seg.v0.x-1,y));  
    }else{//heading==DIR_SOUTH
      for(int y=seg.v0.y-1;y>seg.v1.y-1;y--)
        cells.add(new BCell(seg.v0.x,y));}
    return cells;}
  
  /*
   * ################################
   * 
   * ARBITRARILY CONTIGUOUS CELL GROUP TO LIST OF CONTIGUOUS SUBGROUPS
   * 
   * get mutually contiguous sets of cells
   * given our arbitrarily contiguous square cell group : M
   * remove an arbitrary cell from M : C
   * create new contiguouscellsset from C: S(C)
   * for each cell in M : C0
   *   if C0 is adjacent to any cell in S
   *     remove C0 from M
   *     add C0 to S
   * keep doing this until we can find no cell in M that is adjacent to any cell in S.
   * We now have a contiguous set : S0
   * get S1, S2 etc 
   * get another arbitrary cell C, etc, until M is empty
   * ################################
   */
  
  public static final List<BCellGroup> getContiguousSubgroups(BCellGroup target){
    List<BCell> targetpool=new ArrayList<BCell>(target);
    List<BCellGroup> subgroups=new ArrayList<BCellGroup>();
    BCellGroup subgroup;
    while(!targetpool.isEmpty()){
      subgroup=getContiguousSubGroup(targetpool);
      subgroups.add(subgroup);}
    return subgroups;}
  
  private static BCellGroup getContiguousSubGroup(List<BCell> targetpool){
    BCellGroup subgroup=new BCellGroup();
    BCell cell=targetpool.remove(targetpool.size()-1);
    subgroup.add(cell);
    boolean moved=true;
    while(moved)
      moved=moveCellFromPoolToGroup(targetpool,subgroup);
    return subgroup;}
  
  /*
   * test every cell (c0) in targetpool against every cell (c1) in subgroup 
   *   if c0 is adjacent-flat to c1 (adjacent flat-to-flat, as opposed to corner-to-corner) then 
   *     remove c0 from targetpool
   *     add c0 to subgroup
   *     return true
   * if no cell in targetpool tested agacent against any cell in subgroup then return false. 
   */
  private static boolean moveCellFromPoolToGroup(List<BCell> targetpool,BCellGroup subgroup){
    for(BCell c0:targetpool)
      for(BCell c1:subgroup)
        if(c0.isAdjacentFlat(c1)){
          targetpool.remove(c0);
          subgroup.add(c0);
          return true;}
    return false;}
  
  /*
   * ################################
   * ARBITRARILY CONTIGUOUS CELL GROUP TO SHAPES
   *
   * given cell group : BCellGroup M
   * get contiguous subgroupes within M : list C (see above)
   * for each contiguous group within C create one shape (polygon or yard).
   * ################################
   */
  
  public static final List<BShape> getShapes(BCellGroup agroup){
    List<BCellGroup> cgroups=getContiguousSubgroups(agroup);
    List<BShape> shapes=new ArrayList<BShape>(cgroups.size());
    BShape shape;
    for(BCellGroup cgroup:cgroups){
      shape=getShape(cgroup);
      shapes.add(shape);}
    return shapes;}
  
  /*
   * ################################
   * CONTIGUOUS GROUP TO POLYGON
   * 
   * given contiguous cell group (we're assuming, we aren't gonna test it)
   * get the polygon described by its edge
   * 
   * get all edge cells
   * for each edge cell : c0 
   *   get 1 or 2 edge sections. add to sections list
   * assemble sections into polygons via end-vertex matching
   * 
   * edge sections are clockwise sections of a cell edge
   * our product polygons will be clockwise
   * 
   * ################################
   */
  
  /*
   * TODO this could be optimized
   * we're checking edge cell neighbors twice. Maybe that's bad.
   * 
   * 
   * TODO this must return a SHAPE, NOT A POLYGON
   * because a polygon might have a hole. and then it is contiguous but with multiple polygons.$$$$$$$$$$$$$$$$$
   * 
   * 
   */
  public static BShape getShape(BCellGroup contiguous){
    //get the sections
    List<EdgeSection> sectionpool=new ArrayList<EdgeSection>();
    for(BCell cell:contiguous)
      if(contiguous.isEdge(cell))
        sectionpool.addAll(getEdgeSections(cell,contiguous));
    //assemble them into 1..n polygonal lists of sections
    List<List<EdgeSection>> assembledsectionslist=getSectionLoops(sectionpool);
    List<BPolygon> polygons=getPolygons(assembledsectionslist);
    BShape shape=getShape(polygons);
    return shape;}
  
  private static final BShape getShape(List<BPolygon> polygons){
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
  
  private static final List<BPolygon> getPolygons(List<List<EdgeSection>> assembledsectionslist){
    List<BPolygon> polygons=new ArrayList<BPolygon>(assembledsectionslist.size());
    for(List<EdgeSection> assembledsections:assembledsectionslist)
      polygons.add(getPolygon(assembledsections));
    return polygons;}
  
  private static final BPolygon getPolygon(List<EdgeSection> assembledsections){
    List<BVertex> vertices=new ArrayList<BVertex>();
    for(EdgeSection section:assembledsections)
      vertices.addAll(section.subList(0,section.size()-1));
    BPolygon polygon=new BPolygon(vertices);
    polygon.removeRedundantColinearVertices();
    return polygon;}
  
  private static final List<List<EdgeSection>> getSectionLoops(List<EdgeSection> sectionpool){
    List<List<EdgeSection>> sectionloops=new ArrayList<List<EdgeSection>>();
    while(!sectionpool.isEmpty())
      sectionloops.add(getSectionLoop(sectionpool));
    return sectionloops;}
  
  /*
   * get arbitrary section, remove it from sectionpool
   * init loop list with it
   * while(sectionpool isn't empty)
   *   get the section that fits onto the last section in the loop. 
   *     remove it from sectionpool, add it to loop
   *     if no such section is found then fail. we have noncontiguous cell group.
   *  
   */
  private static final List<EdgeSection> getSectionLoop(List<EdgeSection> sectionpool){
    EdgeSection section=sectionpool.remove(sectionpool.size()-1);
    List<EdgeSection> loop=new ArrayList<EdgeSection>();
    loop.add(section);
    boolean finished=false;
    while(!finished){
      section=getNextSection(loop,sectionpool);
      if(section==null)throw new IllegalArgumentException("noncontiguous cell group");
      sectionpool.remove(section);
      loop.add(section);
      finished=loopIsFinished(loop);}
    return loop;}
  
  /*
   * if the last vertex in the last section is equal to the 
   * first vertex in the first section then the loop is finished
   */
  private static final boolean loopIsFinished(List<EdgeSection> loop){
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
  private static final EdgeSection getNextSection(List<EdgeSection> assembledsections,List<EdgeSection> sectionpool){
    EdgeSection lastsection=assembledsections.get(assembledsections.size()-1);
    BVertex lastvertex=lastsection.get(lastsection.size()-1);
    for(EdgeSection section:sectionpool){
      if(section.get(0).equals(lastvertex))
        return section;}
    return null;}
  
  
  
  private static List<EdgeSection> getEdgeSections(BCell cell,BCellGroup contiguous){
    //map cell edges to boolean array
    boolean[] e={//edges nesw
      cell.getNorth()!=null,
      cell.getEast()!=null,
      cell.getSouth()!=null,
      cell.getWest()!=null};
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
  
  /*
   * edge sections look like this. we have 4 models
   * 
   * o------o
   * 
   * o------o
   *        |
   *        |
   *        |
   *        o
   *        
   * o------o
   *        |
   *        |
   *        |
   * o------o
   * 
   * o------o
   * |      |
   * |      |
   * |      |
   * o------o
   * 
   * An edge section is a list of 2, 3 or 4 vertices
   * the vertices are always addressed in clockwise, index+ direction
   *          
   */
  
  @SuppressWarnings("serial")
  private static class EdgeSection extends ArrayList<BVertex>{
    
    EdgeSection(BVertex... v){
      super(Arrays.asList(v));}
    
    //we use this for when the section is all 4 sides of the cell. closed is true
    EdgeSection(boolean closed,BVertex... v){
      super(Arrays.asList(v));
      this.closed=closed;}
    
    boolean closed=false;//used for whole cell edge section
    //if we've got one of these then our contiguous group must either consist 
    //of just 1 cell or it is noncontiguous and we have an exception
    
  }
  
  
 
}
