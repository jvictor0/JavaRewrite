public class DagInterior extends DagNode {
    //public:
    public DagInterior(final DagMgr mgr, final DagNode head, final DagNode[] tail){
	super(mgr);
	_head = head;
	_tail = tail; 
    }
    
    public DagNode at(int i){
	return i==0 ? _head : _tail[i+1];
    }
    public DagNode head(){
	return at(0);
    }	
    public int size(){
	return _tail.length+1;
    }

    @Override
    public int hashCode(){
	int hash = 0;
	for (int i = 0; i < size(); i++) {
	    hash = 31 * hash + at(i).refHashCode();
	}
	return hash;
    }
   
    @Override
    public boolean equals(Object o){
	if(!(o instanceof DagInterior)){
	    return false;
	}
	DagInterior di = (DagInterior) o;
	//easy disqualification.  They must have same mgr so later check works.  
	if(di.size()!=size() || di.getMgr() != getMgr()){
	    return false;
	}
	for(int i = 0; i < size(); i++){
	    // all children are assumed to be legit, 
	    // so equality means all children are equal as references!
	    if(at(i)!=di.at(i)){
		return false;
	    }
	}
	return true;
    }

    //private:
    private final DagNode _head;
    private final DagNode[] _tail;
}
