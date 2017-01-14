package com.ibm.wala.teavm;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.browser.Window;
import org.teavm.jso.core.JSFunction;
import org.teavm.jso.dom.html.HTMLButtonElement;
import org.teavm.jso.dom.html.HTMLDocument;

import com.ibm.wala.core.tests.basic.GraphDataflowTest;
import com.ibm.wala.teavm.bridge.Constraint;
import com.ibm.wala.teavm.bridge.Constraints;
import com.ibm.wala.teavm.bridge.Hashable;
import com.ibm.wala.teavm.bridge.JSWrapper;
import com.ibm.wala.teavm.test.WalaUtilTests;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.graph.Graph;
import com.ibm.wala.util.graph.NumberedGraph;
import com.ibm.wala.util.graph.impl.SlowSparseNumberedGraph;
import com.ibm.wala.util.graph.traverse.BFSPathFinder;
import com.ibm.wala.util.graph.traverse.Topological;
import com.ibm.wala.util.graph.traverse.WelshPowell;
import com.ibm.wala.util.graph.traverse.WelshPowell.ColoredVertices;

import de.iterable.teavm.jso.browser.Console;

public class Client {

	@JSBody(params = { "message" }, script = "callback(message)")
	public static native void handle(String message);

	private static HTMLDocument document = Window.current().getDocument();

	private static HTMLButtonElement solveNodeButton = document.getElementById("solveNodes").cast();
	private static HTMLButtonElement solveEdgeButton = document.getElementById("solveNodesAndEdges").cast();

	public static void main(String[] args)  {
		solveNodeButton.addEventListener("click", evt -> { 
			solve((Graph<String> G) -> {
				try {
					return GraphDataflowTest.solveNodeOnly(G);
				} catch (CancelException e) {
					return "fail";
				}
			} );
		});

		solveEdgeButton.addEventListener("click", evt -> { 
			solve((Graph<String> G) -> {
				try {
					return GraphDataflowTest.solveNodeEdge(G);
				} catch (CancelException e) {
					return "fail";
				}
			} );
		});

		setSolver((Constraints system) -> {
			SlowSparseNumberedGraph<JSWrapper> G = SlowSparseNumberedGraph.make();	
			copy(system, G);

			new WelshPowell<JSWrapper>().color(G).toString();

			return G;
		});

		setParent((Graph<JSWrapper> G, Hashable a, Hashable b, Hashable c) -> {
			return leastCommonAncestor(G, new JSWrapper(a), new JSWrapper(b), new JSWrapper(c)).getObj();
		});

		setColor((NumberedGraph<JSWrapper> G) -> { 
			ColoredVertices<JSWrapper> colors = (new WelshPowell<JSWrapper>()).color(G);
			return new GetColor() {
				@Override
				public int get(Hashable o) {
					return colors.getColors().get(new JSWrapper(o));
				}
			};
		});

		setTopsort((Graph<JSWrapper> G) -> {
			return Topological.makeTopologicalIter(G);
		});

		setForeach((Iterable<JSWrapper> iter, JSFunction f) -> {
			for(JSWrapper e : iter) {
				f.call(e.getObj());
			}
		});

		new WalaUtilTests();
	}


	public static void copy(Constraints system, Graph<JSWrapper> G) {
		for(int i = 0; i < system.getSize(); i++) {
			Constraint c = system.constraint(i);
			JSWrapper sub = new JSWrapper(c.getSub());
			if (! G.containsNode(sub)) { G.addNode(sub); }
			JSWrapper sup = new JSWrapper(c.getSuper());
			if (! G.containsNode(sup)) { G.addNode(sup); }
			G.addEdge(sup, sub);
		}
	}


	private static JSWrapper leastCommonAncestor(Graph<JSWrapper> G, JSWrapper root, JSWrapper a, JSWrapper b) {
		BFSPathFinder<JSWrapper> apf = new BFSPathFinder<JSWrapper>(G, root, a);
		List<JSWrapper> ap = apf.find();

		BFSPathFinder<JSWrapper> bpf = new BFSPathFinder<JSWrapper>(G, root, b);
		List<JSWrapper> bp = bpf.find();

		for(JSWrapper e : ap) {
			if (bp.contains(e)) {
				return e;
			}
		}

		throw new NoSuchElementException();
	}

	public static void solve(Function<Graph<String>,String> f) {
		Graph<String> G = GraphDataflowTest.buildGraph();
		String solution = f.apply(G);
		Console.log(solution);
		handle(solution);
	}

	@JSFunctor
	public interface Parent extends JSObject {
		Object parent(Graph<JSWrapper> G, Hashable root, Hashable a, Hashable b);
	}

	@JSBody(params = { "s" }, script = "parent = s;")
	public static native void setParent(Parent s);

	@JSFunctor 
	public interface GetColor extends JSObject {
		int get(Hashable o);
	}

	@JSFunctor
	public interface Color extends JSObject {
		GetColor color(NumberedGraph<JSWrapper> G);
	}

	@JSBody(params = { "s" }, script = "color = s;")
	public static native void setColor(Color s);

	@JSFunctor
	public interface Solver extends JSObject {
		Graph<JSWrapper> solve(Constraints o);
	}

	@JSBody(params = { "s" }, script = "solver = s;")
	public static native void setSolver(Solver s);

	@JSFunctor 
	public interface Topsort extends JSObject {
		Iterable<JSWrapper> cb(Graph<JSWrapper> G);
	}

	@JSBody(params = { "s" }, script = "callback = s;")
	public static native void setTopsort(Topsort s);

	@JSFunctor
	public interface Foreach extends JSObject {
		public void foreach(Iterable<JSWrapper> seq, JSFunction fun);
	}

	@JSBody(params = { "f" }, script = "foreach = f;")
	public static native void setForeach(Foreach f);

}
