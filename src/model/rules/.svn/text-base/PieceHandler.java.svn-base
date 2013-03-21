package model.rules;

import model.IPiece;
import visitors.*;

/**
 * A handler class for writing visitor commands for a given piece.
 * 
 * @author Stephen Wong, zpconn (c) 2010
 * @author cps3, psc2
 *
 * @param <R> The return type
 * @param <D> The data type held by the host
 * @param <P> The input parameter type * ----------------------------------------------
 * Restricts command to hosts of type ADataPacket
 */
public abstract class PieceHandler<R, D, P> implements IExtVisitorCmd<R, Class<?>, P, IPiece>{


	/**
	 * The actual method called by the host visitor when the associated case is invoked.   
	 * This method simply forwards the call to the abstract apply method, performing 
	 * an unchecked cast of the host to the required host type.
	 * @param index  The Class object used to identify the host
	 * @param host The host calling the visitor
	 * @param params Vararg input parameters to be used for processing the host
	 * @return The result of this case.
	 */
	@SuppressWarnings("unchecked")
	final public <T extends IExtVisitorHost<Class<?>, ? super IPiece>> R apply(Class<?> index, T host, P... params) {
		return apply(index, (D) host, params);
	}

	/**
	 * Abstract method that actually performs the processing of the case.   
	 * Here, the host is strongly typed to be the DataPacket type appropriate for the case (D).
	 * @param index The host ID identifying the host
	 * @param host  The host calling the visitor
	 * @param params  Vararg input parameter to be used for processing the host
	 * @return  The result of this case.
	 */
	abstract public R apply(Class<?> index, D host, @SuppressWarnings("unchecked") P...params);

}
