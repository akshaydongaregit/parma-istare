public class DirectSearch {

    public Rule rules [] ;

    public void setRules(Rule[] rules){
        this.rules = rules ;    
    }

    public Node searchForGoal(Node start , Node goal){
        Node startNode = start ;
        Node resultNode = performIterativeSearch(startNode,goal);
        return resultNode;
    }

    public Node performIterativeSearch(Node node, Node goal){
     
        if(node!=null && node.getDepth()>10){
            return null;
        }

        for (int i=0; i< this.rules.length; i++){
            Node succ = rules[i].apply(node);

            if(succ!=null) {
                succ.setParent(node);
                succ.setDepth(node.getDepth()+1);

                //System.out.println(" succ : "+succ+" node : "+node+" depth : "+)node.getDepth();

                if(succ.isSame(goal))
                    return succ;
                else if(succ.isRepeated())
                    continue;
                else
                 return performIterativeSearch(succ,goal) ;
            }
        }

        return null;
    }

}
