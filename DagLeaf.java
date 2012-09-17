public class DagLeaf<T> extends DagNode{
    //public:
    public DagLeaf(final DagMgr mgr, final T t){
	super(mgr);
	_data = t;
    }
    public T get(){
	return _data;
    }
    @Override
    public int hashCode(){
	return _data.hashCode();
    }

    @Override
    public  boolean equals(Object o){
	if(!(o instanceof DagLeaf<?>)){
	    return false;
	}
	DagLeaf<?> dl = (DagLeaf<?>) o;
	return _data.equals(dl.get());
    }


    //private:
    private final T _data;
}