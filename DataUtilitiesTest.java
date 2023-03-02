package org.jfree.data;

import static org.junit.Assert.*; import org.junit.*;
import org.junit.rules.ExpectedException;
import org.jmock.*; import org.jfree.data.*;
import java.security.InvalidParameterException;

public class DataUtilitiesTest {
    // setup of keyed value mocking (put in setup)
	// Making a KeyedValue mockery
    Mockery KeyedValuesMock = new Mockery();
    // Creating a mock object of KeyedValues class
    final KeyedValues KeyedValuesMockTable = KeyedValuesMock.mock(KeyedValues.class); 
    
	@BeforeClass
	public static void setUpBeforeCLass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception { 
        
        //Creating expectations of what the values are supposed to be
        KeyedValuesMock.checking(new Expectations() {
            {
            	// Setting up arrays, and testing the methods in KeyedValues
            	double[] keyValues = {10,40,30,20};
            	String[] keys = {"0","1","2","3"};
            	
            	//Loop in order to setup mock values
            	for(int i = 0; i < keyValues.length; i++) {
            		atMost(keys.length).of(KeyedValuesMockTable).getKey(i); 
            		will(returnValue(keys[i].toString()));
            		
            		atMost(keyValues.length).of(KeyedValuesMockTable).getValue(i);
            		will(returnValue(keyValues[i]));
            		
            		atMost(keys.length).of(KeyedValuesMockTable).getIndex(i);
            		will(returnValue(i));
            		
            	}
            	
            	allowing(KeyedValuesMockTable).getKeys();
            	will(returnValue(keys));
            	
            	allowing(KeyedValuesMockTable).getItemCount();
            	will(returnValue(keys.length));
          		
            }
        });
        
	}
	
	// Tests calculateColumnTotal() for two values in a column
	 @Test
	 public void calculateColumnTotalForTwoValues() {
	     // setup
	     Mockery mockingContext = new Mockery();
	     final Values2D values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(2));
	             one(values).getValue(0, 0);
	             will(returnValue(7.5));
	             one(values).getValue(1, 0);
	             will(returnValue(2.5));
	         }
	     });

	     assertEquals("Should return column total of 10.0", 10.0, DataUtilities.calculateColumnTotal(values, 0), .000000001d);

	 }
	
	// Tests for InvalidParameterException when an invalid data object is passed in
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	 
	@Test(expected = InvalidParameterException.class)
	public void calculateColunmTotalForNull() {
		thrown.expect(InvalidParameterException.class);
		thrown.expectMessage("Expected InvalidParameterException");
		final Values2D nullValue = null;
		DataUtilities.calculateColumnTotal(nullValue, 0);
		
	}
	
	// Tests calculateColumnTotal() for a column index that does not exist
	@Test
	public void calculateColumnTotalForInvalidColumn() {
	     Mockery mockingContext = new Mockery();
	     final Values2D values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(3));
	             one(values).getValue(0, -1);
	             will(returnValue(null));
	             one(values).getValue(1, -1);
	             will(returnValue(null));
	             one(values).getValue(2, -1);
	             will(returnValue(null));
	         }
	     });
	     
	     assertEquals("Should return column total of 0.0", 0.0, DataUtilities.calculateColumnTotal(values, -1), .000000001d);
	}
	
	// Tests calculateColumnTotal() for table with multiple columns
	@Test
	public void calculateColumnTotalForTable() {
	     Mockery mockingContext = new Mockery();
	     final Values2D values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getRowCount();
	             will(returnValue(2));
	             one(values).getValue(0, 0);
	             will(returnValue(7.5));
	             one(values).getValue(1, 0);
	             will(returnValue(2.5));
	             one(values).getValue(0, 1);
	             will(returnValue(1.0));
	             one(values).getValue(1, 1);
	             will(returnValue(2.0));
	         }
	     });
	     
	     assertEquals("Should return column total of 3.0", 3.0, DataUtilities.calculateColumnTotal(values, 1), .000000001d);
	}
	
	// Tests calculateRowTotal() for two values in a row
	 @Test
	 public void calculateRowTotalForTwoValues() {
	     // setup
	     Mockery mockingContext = new Mockery();
	     final Values2D values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(2));
	             one(values).getValue(0, 0); //row column
	             will(returnValue(7.5));     //7.5 2.5
	             one(values).getValue(0, 1); //only 7.5
	             will(returnValue(2.5));
	         }
	     });

	     assertEquals("Should return row total of 10.0", 10.0, DataUtilities.calculateRowTotal(values, 0), .000000001d);

	 }
	
	// Tests for InvalidParameterException when an invalid data object is passed in
	@Rule
	public ExpectedException thrown2 = ExpectedException.none();
	 
	@Test(expected = InvalidParameterException.class)
	public void calculateRowTotalForNull() {
		thrown.expect(InvalidParameterException.class);
		thrown.expectMessage("Expected InvalidParameterException");
		final Values2D nullValue = null;
		DataUtilities.calculateRowTotal(nullValue, 0);
		
	}
	
	
	
	// Tests calculateRowTotal() for a row index that does not exist
	@Test
	public void calculateRowTotalForInvalidRow() {
	     Mockery mockingContext = new Mockery();
	     final Values2D values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(3));
	             one(values).getValue(-1, 0);
	             will(returnValue(null));
	             one(values).getValue(-1, 1);
	             will(returnValue(null));
	             one(values).getValue(-1, 2);
	             will(returnValue(null));
	         }
	     });
	     
	     assertEquals("Should return row total of 0.0", 0.0, DataUtilities.calculateRowTotal(values, -1), .000000001d);
	}
	
	// Tests calculateRowTotal() for table with multiple rows
	@Test
	public void calculateRowTotalForTable() {
	     Mockery mockingContext = new Mockery();
	     final Values2D values = mockingContext.mock(Values2D.class);
	     mockingContext.checking(new Expectations() {
	         {
	             one(values).getColumnCount();
	             will(returnValue(2));
	             one(values).getValue(0, 0); // 7.5 2.5
	             will(returnValue(7.5));     // 1.0 2.0
	             one(values).getValue(0, 1); //only 1.0
	             will(returnValue(2.5));
	             one(values).getValue(1, 0);
	             will(returnValue(1.0));
	             one(values).getValue(1, 1);
	             will(returnValue(2.0));
	         }
	     });
	     
	     assertEquals("Should return row total of 3.0", 3.0, DataUtilities.calculateRowTotal(values, 1), .000000001d);
	}
	
		@Test // Tests the first value for the getCumulativePercentages() method.
		public void getCumulativePercetagesTestValue1() {
		KeyedValues finalTest = DataUtilities.getCumulativePercentages(KeyedValuesMockTable);
		assertEquals("The first percentage is 10/100, it should be 0.10"
		, 0.1, (double) finalTest.getValue(0), .000000001d);
}
		
		@Test // Tests the second value for the getCumulativePercentages() method.
		public void getCumulativePercetagesTestValue2() {
			KeyedValues finalTest = DataUtilities.getCumulativePercentages(KeyedValuesMockTable);
			assertEquals("The second percentage is 50/100, it should be 0.50"
			, 0.5, (double) finalTest.getValue(1), .000000001d);
}
	
 		@Test // Tests the third value for the getCumulativePercentages() method.
 		public void getCumulativePercetagesTestValue3() {
 			KeyedValues finalTest = DataUtilities.getCumulativePercentages(KeyedValuesMockTable);
 			assertEquals("The third percentage is 80/100, it should be 0.80"
 			, 0.8, (double) finalTest.getValue(2), .000000001d);
    }
	
	 	@Test // Tests the fourth value for the getCumulativePercentages() method.
	    public void getCumulativePercetagesTestValue4() {
	    	KeyedValues finalTest = DataUtilities.getCumulativePercentages(KeyedValuesMockTable);
	        assertEquals("The fourth percentage is 100/100, it should be 1.0"
	        , 1.0, (double) finalTest.getValue(3), .000000001d);
	    }

	 	@Rule // Testing InvalidParameterException for getCumulativePercentages with an invalid input
		public ExpectedException invalidException = ExpectedException.none();
		
		@Test (expected = InvalidParameterException.class)
		public void getCumulativePercentagesInvalid() {
			invalidException.expect(InvalidParameterException.class);
			invalidException.expectMessage("Expected InvalidParameterException");
			final KeyedValues KeyedNullValue = null;
			DataUtilities.getCumulativePercentages(KeyedNullValue);
		}
	//createNumberArray
		@Test
		//testing positive decimals values
		public void testCreateNumberArrayPositive() {
			double[] testData = {1.1, 2.2, 3.3, 4.4, 5.5, 7.7, 8.8};
			Number[] result = DataUtilities.createNumberArray(testData);
			assertTrue(result instanceof Number[]);
		}
		@Test
		//testing negative decimals values
		public void testCreateNumberArrayNegative() {
			double[] testData = {-1.1, -2.2, -3.3, -4.4, -5.5, -7.7, -8.8};
			Number[] result = DataUtilities.createNumberArray(testData);
			assertTrue(result instanceof Number[]);
		}
		@Test
		//testing both positive and negative values
		public void testCreateNumberArrayAll() {
			double[] testData = {-4.4, -3.3, -2.2, -1.1, 1.1, 2.2, 3.3};
			Number[] result = DataUtilities.createNumberArray(testData);
			assertTrue(result instanceof Number[]);
		}
		@Rule // Testing InvalidParameterException for createNumberArray with an invalid input
		public ExpectedException invalidNumArrayException = ExpectedException.none();
		
		// Testing InvalidParameterException for createNumberArray with an invalid input
		@Test(expected = IllegalArgumentException.class)
		public void createNumberArrayInvalid() {
			invalidNumArrayException.expect(InvalidParameterException.class);
			invalidNumArrayException.expectMessage("Expected InvalidParameterException");
			final double[] numArray = null;
			DataUtilities.createNumberArray(numArray);
		}
		
		//createNumberArray2D
		@Test
		//testing positive decimals values
		public void testCreateNumberArray2DPositive() {
			double[][] testData = {{1.1, 2.2}, {3.3, 4.4}};
			Number[][] result = DataUtilities.createNumberArray2D(testData);
			assertTrue(result instanceof Number[][]);
		}
		@Test
		//testing negative decimals values
		public void testCreateNumberArray2DNegative() {
			double[][] testData = {{-1.1, -2.2}, { -3.3, -4.4}};
			Number[][] result = DataUtilities.createNumberArray2D(testData);
			assertTrue(result instanceof Number[][]);
		}
		@Test
		//testing both positive and negative values
		public void testCreateNumberArray2DAll() {
			double[][] testData = {{ -2.2, -1.1}, {1.1, 2.2}};
			Number[][] result = DataUtilities.createNumberArray2D(testData);
			assertTrue(result instanceof Number[][]);
		}
		
		@Rule // Testing InvalidParameterException for createNumberArray with an invalid input
		public ExpectedException invalidNumArray2DException = ExpectedException.none();
		
		// Testing InvalidParameterException for createNumberArray with an invalid input
		@Test(expected = IllegalArgumentException.class)
		public void createNumberArray2DInvalid() {
			invalidNumArray2DException.expect(InvalidParameterException.class);
			invalidNumArray2DException.expectMessage("Expected InvalidParameterException");
			final double[][] numArray2D = null;
			DataUtilities.createNumberArray2D(numArray2D);
		}
		
		//clone(double[][])
		@Test
		//testing clone method with 2D array
		public void testClone() {
			double[][] originalArray = {{1.1, 2.2}, {3.3, 4.4}};
			double[][] copy = DataUtilities.clone(originalArray);
			boolean outcome = DataUtilities.equal(originalArray, copy);
			assertTrue("Copy should be the same as orginal", outcome);
		}
}
