package com.ibm.wala.teavm.test;

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
	
	public static void walaUtilTests() {
		runTest(() -> { (new ExtensionGraphTest()).testAugment(); });
		runTest(() -> { (new FloydWarshallTest()).TestPathLengths(); });
		runTest(() -> { (new FloydWarshallTest()).TestShortestPath(); });
		runTest(() -> { (new FloydWarshallTest()).TestShortestPaths(); });
		runTest(() -> { (new GraphDataflowTest()).testSolverNodeOnly(); });
		runTest(() -> { (new GraphDataflowTest()).testSolverNodeEdge(); });
		runTest(() -> { (new OrdinalSetTest()).test1(); });
		runTest(() -> { (new PathFinderTest()).testPaths1(); });
		runTest(() -> { (new PathFinderTest()).testPaths2(); });
		runTest(() -> { (new PathFinderTest()).testPaths3(); });
		runTest(() -> { (new PathFinderTest()).testPaths4(); });
		runTest(() -> { (new PrimitivesTest()).testBFSPathFinder(); });
		runTest(() -> { (new PrimitivesTest()).testBimodalMap(); });
		runTest(() -> { (new PrimitivesTest()).testBimodalMutableSparseIntSet(); });
		runTest(() -> { (new PrimitivesTest()).testBinaryIntegerRelation(); });
		runTest(() -> { (new PrimitivesTest()).testBitVector(); });
		runTest(() -> { (new PrimitivesTest()).testBitVectorIntSet(); });
		runTest(() -> { (new PrimitivesTest()).testBitVectors(); });
		runTest(() -> { (new PrimitivesTest()).testBoundedBFS(); });
		runTest(() -> { (new PrimitivesTest()).testDominatorsA(); });
		runTest(() -> { (new PrimitivesTest()).testMutableSharedBitVectorIntSet(); });
		runTest(() -> { (new PrimitivesTest()).testMutableSparseIntSet(); });
		// runTest(() -> { (new PrimitivesTest()).testMutableSparseLongSet(); });
		runTest(() -> { (new PrimitivesTest()).testOffsetBitVector0_10(); });
		runTest(() -> { (new PrimitivesTest()).testOffsetBitVector100_10(); });
		runTest(() -> { (new PrimitivesTest()).testOffsetBitVector10_10(); });
		runTest(() -> { (new PrimitivesTest()).testOffsetBitVector50_10(); });
		runTest(() -> { (new PrimitivesTest()).testOffsetBitVector50_50(); });
		runTest(() -> { (new PrimitivesTest()).testOffsetBitVectors100_200_10(); });
		runTest(() -> { (new PrimitivesTest()).testOffsetBitVectors100_25_10(); });
		runTest(() -> { (new PrimitivesTest()).testOffsetBitVectors150_10(); });
		runTest(() -> { (new PrimitivesTest()).testOffsetBitVectors35_25_20_10(); });
		runTest(() -> { (new PrimitivesTest()).testSemiSparseMutableIntSet(); });
		runTest(() -> { (new PrimitivesTest()).testSmallMap(); });
		runTest(() -> { (new PrimitivesTest()).testSpecificBugsInOffsetBitVectors(); });
		runTest(() -> { (new PrimitivesTest()).testSpecificBugsInSemiSparseMutableIntSets(); });
		runTest(() -> { (new PrimitivesTest()).testUnionFind(); });
		runTest(() -> { (new WelshPowellTest()).testOne(); });
		runTest(() -> { (new WelshPowellTest()).testTwo(); });
		runTest(() -> { (new SemiSparseMutableIntSetTest()).testCase1(); });
		runTest(() -> { (new TwoLevelVectorTest()).testCase1(); });
	}

	private interface Test {
		void test() throws CancelException;
	}

	private static void runTest(Test test) {
		Console.log("running " + test.getClass().getName());
		try {
			test.test();
		} catch (Throwable e) {
			Console.log("teat failed with " + e.toString());
			e.printStackTrace();
		}
	}

}
