import java.util.*;

public class WaterJug
{
	public static int JUG1 = 4 ;
	public static int JUG2 = 3 ;
	
	public static void main(String[] args)
	{
		Rule rules[] = defineRules(4,3);
		Node start = new Node(0,0);

		rules[0].apply(start);
	}

	public static Rule[] defineRules(int jug1,int jug2){
		Rule[] rules = new Rule[6];

		// fill jug 1
		rules[0] = new Rule(){
			public Node apply(Node node){
				if(node.getX()==0)
					node.setX(Main.JUG1);
				else 
					node = null ;
					
				return node;
			}
		};
		
		//fill jug 2 
		rules[1] = new Rule(){
			public Node apply(Node node){
				if(node.getY()==0)
					node.setY(Main.JUG2);
				else 
					node = null ;

				return node;
			}
		};
		
		//empty jug 1 
		rules[2] = new Rule(){
			public Node apply(Node node){
				if(node.getX() > 0)
					node.setX(0);
				else 
					node = null ;

				return node;
			}
		};
		
		//empty jug 2
		rules[3] = new Rule(){
			public Node apply(Node node){
				if(node.getY()==0)
					node.setY(0);
				else 
					node = null ;

				return node;
			}
		};
		
		//pour jug2 into jug1
		rules[0] = new Rule(){
			public Node apply(Node node){
				if(node.getX()==0)
					node.setX(Main.JUG1);
				else 
					node = null ;

				return node;
			}
		};
		
		return rules;
	}
}
