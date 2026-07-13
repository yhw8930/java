---
name: explain
description: Explain algorithm problems and source code by writing clear, teaching-oriented Chinese comments above algorithm entry methods in source files. Use when the user asks to explain or annotate a Java/Go/C++/Python algorithm file, understand code or a recurrence, analyze correctness or bugs, trace an example, or state time and space complexity. When a source file is identified, edit that file by default unless the user explicitly requests a read-only explanation.
---

# Explain Algorithms

## Goal

Make an algorithm understandable inside its source file, without requiring the reader to infer the problem, invariants, or hidden boundary conditions from the implementation. Prefer precise, teaching-oriented Chinese and preserve the source code's original behavior.

## Workflow

1. Read the problem statement and all relevant code before explaining individual lines.
2. Reconstruct missing rules from names, parameters, comments, and implementation, but label uncertain assumptions.
3. Check correctness before presenting the code as a valid solution. Point out bugs, swapped fields, precedence traps, overflow risks, invalid base cases, and missing constraints with a concrete counterexample when possible.
4. Explain the solution from the problem model to the implementation:
   - What the input represents and what must be returned.
   - The core idea and why it works.
   - The meaning of important variables, states, return values, or data structures.
   - The base cases and transition/recurrence.
   - A small execution trace when it materially improves understanding.
   - Time and space complexity with the variables used in the analysis.
5. When a source file is named, selected, or clearly implied by the request, write the explanation above its algorithm entry method or methods by default. Do not stop after returning a chat-only explanation. Treat phrases such as `explain`, “解释一下”, or “讲解这题” together with a file/class name as authorization to add comments to that source file.
6. Do not edit a file only when the user explicitly requests a read-only review, says not to modify files, or provides code with no identifiable writable file.
7. Before editing, identify comments that already exist in the file. Preserve the original class-level problem statement verbatim: do not rewrite, delete, move, merge with, or reformat it. Preserve other existing comments unless the user asks to change them.
8. Change comments only. Preserve imports, declarations, formatting, and executable logic unless the user separately requests a bug fix or refactor. If a bug blocks a truthful explanation, describe it in the entry-method comment and report it; ask before making a behavior-changing fix unless fixing was requested.
9. Match the reader's level. Start with intuition, then connect it to exact code conditions.

## Comment Style

- Add a solution comment immediately above each algorithm entry method. Explain the core idea, important state meanings, correctness conditions, and time/space complexity.
- Keep the problem statement in its original class-level comment. Do not repeat or paraphrase the problem statement in newly added method comments.
- Preserve problem and test links already present in the source.
- Put all newly written explanation in those entry-method comments.
- Do not add comments to the class, fields, constructors, helper methods, or inside method bodies.
- When several public methods are independent algorithm entry points, give each one its own comment. Do not treat a public helper as an entry point merely because it is public.
- Explain key decisions, invariants, non-obvious state meanings, base cases, and transitions in the entry-method comment rather than beside individual lines.
- Use plain comment text. Do not emit HTML or Javadoc formatting tags such as `<p>`, `<pre>`, `<code>`, `<ul>`, or `<br>`.
- Keep terminology consistent with the code. For example, distinguish nodes from values, indices from counts, and subtree information from global information.
- Explain sentinel values such as `-1`, `null`, `math.MaxInt`, or empty slices/maps.
- For recursive code, state what each call promises to return before explaining how the caller combines results.
- For dynamic programming, define every dimension/state before giving the transition and iteration order.
- For graph algorithms, state directed/undirected assumptions, weight restrictions, and whether disconnected nodes are possible.
- After editing, inspect the diff and verify that all executable lines are unchanged when the request was explanation-only.

Preferred Go format:

```go
// Dijkstra1 求 from 到所有可达节点的最短距离，要求边权非负。
// 核心思路：每轮确定一个尚未处理且当前距离最小的节点，再用它松弛邻边。
//
// 时间复杂度：O(V^2+E)。
// 空间复杂度：O(V)。
func Dijkstra1(from *Node) map[*Node]int {
```

Preferred Java format:

```java
/**
 * 判断一棵二叉树是否为二叉搜索树。
 * 递归返回每棵子树是否为 BST，以及子树的最小值和最大值。
 * 时间复杂度：O(N)；递归额外空间：O(H)。
 */
public boolean isValidBST(TreeNode root) {
```

## Correctness And Bug Explanations

- Lead with the conclusion: correct, incorrect, or correct only under stated constraints.
- Cite the exact condition or data flow responsible for the result.
- Use the smallest counterexample that reaches the faulty branch.
- Separate correctness bugs from readability issues, unused imports, naming problems, and style suggestions.
- Respect language operator precedence, integer ranges, reference/value semantics, and library behavior rather than guessing from formatting.
- If multiple implementations exist, compare their invariants, complexity, and failure modes instead of only restating syntax.

## Response Shape

Use only the sections that help the specific question. A typical explanation is:

1. Conclusion or core idea.
2. Step-by-step mapping from algorithm to code.
3. Example trace or counterexample.
4. Complexity.
5. The source file updated with comments, plus a concise summary of what was annotated.

Keep short questions concise. Expand when the user asks for a detailed lesson or when the algorithm depends on a subtle invariant.
