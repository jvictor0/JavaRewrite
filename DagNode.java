
public abstract class DagNode extends Object {
    //public:
    public DagNode(final DagMgr mgr){
	_mgr = mgr;
    }

    final public DagMgr getMgr(){
	return _mgr;
    }
    
    final public int refHashCode(){
	return super.hashCode();
    }



    //private:
    private final DagMgr _mgr;
}


