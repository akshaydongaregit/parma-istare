public class Node
{
	
	Node parent = null;
	int x,y;
	int depth=0;

	public Node(int x,int y){
		this.x = x ;
		this.y = y ;
	}

	public void setX(int x){
		this.x = x;
	}
	public int getX(){
		return x;
	}
	public void setY(int y){
		this.y = y ;
	}
	public int getY(){
		return y;
	}
	public void setParent(Node parent){
		this.parent = parent ;
	}
	public Node getParent(){
		return this.parent;
    }
    
    public boolean isSame(Node node){
        return ( this.x == node.getX() && this.y == node.getY() ) ;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}

	@Override
	public String toString(){
		String toString = "["+x+","+y+"]" ;
		
		Node iparent = this.parent ;
		 while(iparent != null){
			//System.out.print(".");
			toString += " <-- ["+iparent.getX()+","+iparent.getY()+"]" ;
			iparent = iparent.getParent();
		}

		return toString;
	}

	public boolean isRepeated(){
		Node iparent = this.parent ;
		while(iparent != null){
			if(this.isSame(iparent))
				return true;
			iparent = iparent.getParent();
		}

		return false;
	}

}
