import java.util.*;

public class WaterJug
{
	public static int JUG1 = 4 ;
	public static int JUG2 = 3 ;
	
	public static void main(String[] args)
	{
		Rule rules[] = defineRules();
		Node start = new Node(0,0);
        Node goal = new Node(2,0);
        
        DirectSearch search = new DirectSearch();
        search.setRules(rules);

        Node result = search.searchForGoal(start,goal);

        System.out.println(" result : "+result);

	}


	public static Rule[] defineRules(){
		Rule[] rules = new Rule[6];

		// fill jug 1
		rules[0] = new Rule(){
			public Node apply(Node in){
                Node node = new Node(in.getX(),in.getY());
                //System.out.print("\nApplying 1 on ["+node.getX()+" , "+node.getY()+"]");
				if(node.getX()==0)
					node.setX(WaterJug.JUG1);
				else 
					node = null ;
                /*if(node!=null)
                System.out.print("-->["+node.getX()+" , "+node.getY()+"]");*/

				return node;
			}
		};
		
		//fill jug 2 
		rules[1] = new Rule(){
			public Node apply(Node in){
                Node node = new Node(in.getX(),in.getY());
                
                //System.out.print("\nApplying 2 on ["+node.getX()+" , "+node.getY()+"]");

				if(node.getY()==0)
					node.setY(WaterJug.JUG2);
				else 
					node = null ;
                /*if(node!=null)
                    System.out.print("-->["+node.getX()+" , "+node.getY()+"]"); */
				return node;
			}
		};
		
		
		//pour jug2 into jug1
		rules[2] = new Rule(){
			public Node apply(Node in){
                Node node = new Node(in.getX(),in.getY());

                //System.out.print("\nApplying 3 on ["+node.getX()+" , "+node.getY()+"]");
                
                if( node.getY() > 0 && node.getX()<WaterJug.JUG1)
                    if(node.getX()+node.getY() <= WaterJug.JUG1){
                        node.setX(node.getX()+node.getY());
                        node.setY(0);
                    }
                    else{
                        node.setY(node.getY()-(WaterJug.JUG1-node.getX()));
                        node.setX(WaterJug.JUG1);
                    }
				else 
					node = null ;
                /* if(node!=null)
                    System.out.print("-->["+node.getX()+" , "+node.getY()+"]"); */
				return node;
			}
		};
        
        //pour jug1 into jug2
		rules[3] = new Rule(){
			public Node apply(Node in){
                Node node = new Node(in.getX(),in.getY());

                //System.out.print("\nApplying 4 on ["+node.getX()+" , "+node.getY()+"]");
                
                if( node.getX() > 0 && node.getY()<WaterJug.JUG2)
                    if(node.getX()+node.getY() <= WaterJug.JUG2){
                        node.setY(node.getX()+node.getY());
                        node.setX(0);
                    }
                    else{
                        node.setX(node.getX()-(WaterJug.JUG2-node.getY()));
                        node.setY(WaterJug.JUG2);
                    }
                else 
                    node = null ;

                /* if(node!=null)
                        System.out.print("-->["+node.getX()+" , "+node.getY()+"]"); */

				return node;
			}
        };
        
        
		//empty jug 1 
		rules[4] = new Rule(){
			public Node apply(Node in){
                Node node = new Node(in.getX(),in.getY());
                
                //System.out.print("\nApplying 5 on ["+node.getX()+" , "+node.getY()+"]");
                
                if(node.getX() > 0)
					node.setX(0);
				else 
					node = null ;
                /* if(node!=null)
                    System.out.print("-->["+node.getX()+" , "+node.getY()+"]"); */

				return node;
			}
		};
		
		//empty jug 2
		rules[5] = new Rule(){
			public Node apply(Node in){
                Node node = new Node(in.getX(),in.getY());

                //System.out.print("\nApplying 6 on ["+node.getX()+" , "+node.getY()+"]");
                
                if(node.getY() > 0)
					node.setY(0);
				else 
					node = null ;
                
                /*if(node!=null)
                    System.out.print("-->["+node.getX()+" , "+node.getY()+"]"); */

				return node;
			}
        };
        
		return rules;
    }
    
}
