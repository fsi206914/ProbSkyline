package org.liang.KDTree;

import java.lang.reflect.Array;

/**
 * A Point implementation supporting k dimensions.
 */
public class KDPoint implements  Comparable< KDPoint>
{
	public  double[] __coordinates;
	public int currDimComp;

	/**
	 * Constructs a GenericPoint with the specified dimensions.
	 *
	 * @param dimensions The number of dimensions in the point.  Must be
	 * greater than 0.
	 */
	public KDPoint( int dimensions) {
		assert(dimensions > 0);
		__coordinates = new double[dimensions];
		currDimComp=0;
	}

	/**
	 * Two-dimensional convenience constructor.
	 *
	 * @param x The coordinate value of the first dimension.
	 * @param y The coordinate value of the second dimension.
	 */
	public KDPoint( double x, double y) {
		__coordinates = new double[2];
		setCoord(0, x);
		setCoord(1, y);
	}


	public KDPoint( double x, double y, double z) {
		__coordinates = new double[3];
		setCoord(0, x);
		setCoord(1, y);
		setCoord(2, z);
	}

	/**
	 * Sets the value of the coordinate for the specified dimension.
	 *
	 * @param dimension The dimension (starting from 0) of the
	 * coordinate value to set.
	 * @param value The new value of the coordinate.
	 * @exception ArrayIndexOutOfBoundsException If the dimension is
	 * outside of the range [0,getDimensions()-1].
	 */
	public void setCoord(int dimension, double value)
		throws ArrayIndexOutOfBoundsException
		{
			__coordinates[dimension] = value;
		}

	/**
	 * Returns the value of the coordinate for the specified dimension.
	 *
	 * @param dimension The dimension (starting from 0) of the
	 * coordinate value to retrieve.
	 * @return The value of the coordinate for the specified dimension.
	 * @exception ArrayIndexOutOfBoundsException If the dimension is
	 * outside of the range [0,getDimensions()-1].
	 */
	public double getCoord(int dimension) {
		return __coordinates[dimension];
	}

	/**
	 * Returns the number of dimensions of the point.
	 *
	 * @return The number of dimensions of the point.
	 */
	public int getDim() { return __coordinates.length; }


	/**
	 * Returns a string representation of the point, listing its
	 * coordinate values in order.
	 *
	 * @return A string representation of the point.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("[ ");
		buffer.append(__coordinates[0]);

		for(int i = 1; i < __coordinates.length; ++i) {
			buffer.append(", ");
			buffer.append(__coordinates[i]);
		}

		buffer.append(" ]");

		return buffer.toString();
	}


	public void setCurrDimComp(int a_dim){

		currDimComp = a_dim;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(final KDPoint o) {

		if(this.__coordinates[currDimComp] >  o.__coordinates[currDimComp]) return 1;
		else if(this.__coordinates[currDimComp] <  o.__coordinates[currDimComp]) return -1;
		else return 0;
	}


	public static void main(String args[]){

		KDPoint a = new KDPoint(5);
		a.setCoord(0, 1.1);a.setCoord(1, 2.1);a.setCoord(2, 3.1);
		//System.out.println(a.toString());
		KDPoint b = new KDPoint(3);
		b.setCoord(0, 1.2);a.setCoord(1, 2.2);a.setCoord(2, 3.2);
		System.out.println(a.compareTo(b));
	}
}
