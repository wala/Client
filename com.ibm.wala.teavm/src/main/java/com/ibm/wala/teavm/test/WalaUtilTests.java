package com.ibm.wala.teavm.test;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

import com.ibm.wala.core.tests.basic.ExtensionGraphTest;
import com.ibm.wala.core.tests.basic.FloydWarshallTest;
import com.ibm.wala.core.tests.basic.GraphDataflowTest;
import com.ibm.wala.core.tests.basic.OrdinalSetTest;
import com.ibm.wala.core.tests.basic.PathFinderTest;
import com.ibm.wala.core.tests.basic.PrimitivesTest;
import com.ibm.wala.core.tests.basic.WelshPowellTest;
import com.ibm.wala.core.tests.collections.SemiSparseMutableIntSetTest;
import com.ibm.wala.core.tests.collections.TwoLevelVectorTest;
import com.ibm.wala.util.CancelException;

import de.iterable.teavm.jso.browser.Console;

public class WalaUtilTests {
	
	private interface Test extends JSObject {
		void test() throws CancelException;
	}

	@JSBody(params = { "test" }, script = "walaUnitTests[ walaUnitTests.length ] = test;")
	private static native void installTest(Test test);
	
	@JSFunctor
	private interface TestRunner extends JSObject {
		boolean runTest(Test test) throws CancelException;
	}

	@JSBody(params = { "runner" }, script = "testRunner = runner;")
	private static native void installTestRunner(TestRunner runner);

	public WalaUtilTests() {
		installTest(() -> { (new ExtensionGraphTest()).testAugment(); });
		installTest(() -> { (new FloydWarshallTest()).TestPathLengths(); });
		installTest(() -> { (new FloydWarshallTest()).TestShortestPath(); });
		installTest(() -> { (new FloydWarshallTest()).TestShortestPaths(); });
		installTest(() -> { (new GraphDataflowTest()).testSolverNodeOnly(); });
		installTest(() -> { (new GraphDataflowTest()).testSolverNodeEdge(); });
		installTest(() -> { (new OrdinalSetTest()).test1(); });
		installTest(() -> { (new PathFinderTest()).testPaths1(); });
		installTest(() -> { (new PathFinderTest()).testPaths2(); });
		installTest(() -> { (new PathFinderTest()).testPaths3(); });
		installTest(() -> { (new PathFinderTest()).testPaths4(); });
		installTest(() -> { (new PrimitivesTest()).testBFSPathFinder(); });
		installTest(() -> { (new PrimitivesTest()).testBimodalMap(); });
		installTest(() -> { (new PrimitivesTest()).testBimodalMutableSparseIntSet(); });
		installTest(() -> { (new PrimitivesTest()).testBinaryIntegerRelation(); });
		installTest(() -> { (new PrimitivesTest()).testBitVector(); });
		installTest(() -> { (new PrimitivesTest()).testBitVectorIntSet(); });
		installTest(() -> { (new PrimitivesTest()).testBitVectors(); });
		installTest(() -> { (new PrimitivesTest()).testBoundedBFS(); });
		installTest(() -> { (new PrimitivesTest()).testDominatorsA(); });
		installTest(() -> { (new PrimitivesTest()).testMutableSharedBitVectorIntSet(); });
		installTest(() -> { (new PrimitivesTest()).testMutableSparseIntSet(); });
		installTest(() -> { (new PrimitivesTest()).testMutableSparseLongSet(); });
		installTest(() -> { (new PrimitivesTest()).testOffsetBitVector0_10(); });
		installTest(() -> { (new PrimitivesTest()).testOffsetBitVector100_10(); });
		installTest(() -> { (new PrimitivesTest()).testOffsetBitVector10_10(); });
		installTest(() -> { (new PrimitivesTest()).testOffsetBitVector50_10(); });
		installTest(() -> { (new PrimitivesTest()).testOffsetBitVector50_50(); });
		installTest(() -> { (new PrimitivesTest()).testOffsetBitVectors100_200_10(); });
		installTest(() -> { (new PrimitivesTest()).testOffsetBitVectors100_25_10(); });
		installTest(() -> { (new PrimitivesTest()).testOffsetBitVectors150_10(); });
		installTest(() -> { (new PrimitivesTest()).testOffsetBitVectors35_25_20_10(); });
		installTest(() -> { (new PrimitivesTest()).testSemiSparseMutableIntSet(); });
		installTest(() -> { (new PrimitivesTest()).testSmallMap(); });
		installTest(() -> { (new PrimitivesTest()).testSpecificBugsInOffsetBitVectors(); });
		installTest(() -> { (new PrimitivesTest()).testSpecificBugsInSemiSparseMutableIntSets(); });
		installTest(() -> { (new PrimitivesTest()).testUnionFind(); });
		installTest(() -> { (new WelshPowellTest()).testOne(); });
		installTest(() -> { (new WelshPowellTest()).testTwo(); });
		installTest(() -> { (new SemiSparseMutableIntSetTest()).testCase1(); });
		installTest(() -> { (new TwoLevelVectorTest()).testCase1(); });
	
		installTestRunner((Test test) -> { return runTest(test); });
	}

	private static boolean runTest(Test test) throws CancelException {
		Console.log("running " + test.getClass().getName());
			test.test();
			return true;
	}

}
