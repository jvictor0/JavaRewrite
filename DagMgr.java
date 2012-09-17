import java.util.HashSet;
import java.util.HashMap;
import java.lang.ref.WeakReference;
import java.lang.ref.ReferenceQueue;


//is there really no typedef?

public class DagMgr extends Object {

    //public:
    public DagMgr(){
	_dag_table = new DagTableRef();
    }
    
    public DagNode createNode(final DagNode head, final DagNode[] body){
	return registerNode(new DagInterior(this,head,body));
    }
    public <T extends Object> DagNode createNode(final T t){
	return registerNode(new DagLeaf<T>(this,t));
    }

    public int size(){
	return _dag_table.size();
    }
    

    //private:    
    private DagNode registerNode(final DagNode node){
	while(true){
	    collect();
	    WeakDagNode noderef = new WeakDagNode(node,_ref_q);
	    WeakDagNode oldref = _dag_table.get(noderef);
	    if(oldref == null){
		_dag_table.put(noderef,noderef);
		return node;
	    }
	    DagNode result = oldref.get(); // Now the result cannot be GC'd and enqueued.  
                                           // It could however already be in the RQ.
	                                   // It is unclear if result is garbage or what.
	    if(result == null){
		// If we get here, presumably, the old ref is on its way to being collected.  
		// I worry, however, about looping forever, or for a long time, if there is a 
		// while between enqueing and GC'ing.  
		_dag_table.remove(oldref);
		continue;
	    }
	    return result;
	}
    }
    
    /*
     * I am a bit scared about this.  
     * What if between a call to collect and the end of registerNode
     * the node which is returned is GC'd and added to the queue.
     * Then we are doomed.  
     */
    private void collect(){
	WeakDagNode dn = (WeakDagNode) _ref_q.poll();
	while(dn != null){
	    _dag_table.remove(dn);
	}
    }


    // member variables
    private DagTableRef _dag_table;
    private ReferenceQueue<DagNode> _ref_q;



    // cannot typedef, so I will do this I guess.  
    private class WeakDagNode extends WeakReference<DagNode>{
	public WeakDagNode(DagNode dn, ReferenceQueue<DagNode> rq){
	    super(dn,rq);
	}
	
	@Override
	public boolean equals(Object o){
	    if(o instanceof WeakDagNode){
		return get().equals(((WeakDagNode)o).get());
	    }
	    return false;
	}
	@Override
        public int hashCode(){
	    return get().hashCode();
	}

    }
    private class DagTableRef extends HashMap<WeakDagNode, WeakDagNode> {
	// if i don't do this, will bad bad things happen?
	public DagTableRef(){
	    super(); 
	}
    }
}

