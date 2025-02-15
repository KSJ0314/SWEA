import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class 김소중 {
	public static void main(String[] args) throws IOException {
		//System.setIn(new FileInputStream("./res/input.txt"));
		김소중 s = new 김소중();
		s.init();
		System.out.println(s.sb.toString());
	}
	
	StringBuilder sb = new StringBuilder();
	int[][] dels = { {0, 0} ,{ -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
	int[][] aps;
	int[] userA, userB, coorsA, coorsB;
	boolean[][][] p;
	int a, m;
	int sum;
	
	public void fnc() {
		for (int i = 0; i <= m; i++) {
			int ay = coorsA[0];
			int ax = coorsA[1];
			int by = coorsB[0];
			int bx = coorsB[1];
			
			List<Integer> listA = new ArrayList<>();
			List<Integer> listB = new ArrayList<>();
			List<Integer> listAll = new ArrayList<>();
			int count = 0;
			for (int j = 0; j < a; j++) {
				count++;
				if (p[j][ay][ax] && p[j][by][bx]) {
					listAll.add(aps[j][3]);
				} else if (p[j][ay][ax]) {
					listA.add(aps[j][3]);
				} else if (p[j][by][bx]) {
					listB.add(aps[j][3]);
				} else {
					count--;
				}
			}
			
			if (count == 1) {
				if (!listA.isEmpty()) {
					sum += listA.get(0);
				} else if (!listB.isEmpty()) {
					sum += listB.get(0);
				} else {
					sum += listAll.get(0);
				}
			} else if (count >= 2) {
				int aMax = listA.stream().sorted(Comparator.reverseOrder()).findFirst().orElse(0);
				int bMax = listB.stream().sorted(Comparator.reverseOrder()).findFirst().orElse(0);
				listAll.add(aMax);
				listAll.add(bMax);
				
				sum += listAll.stream()
						.sorted(Comparator.reverseOrder())
						.limit(2)
						.mapToInt(Integer::valueOf)
						.sum();
			}
			
			if (i < m) {
				coorsA[0] += dels[userA[i]][0];
				coorsA[1] += dels[userA[i]][1];
				coorsB[0] += dels[userB[i]][0];
				coorsB[1] += dels[userB[i]][1];
			}
		}
	}
	
	
	public void init() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			
			int t = Integer.parseInt(br.readLine());
			for (int test_case = 1; test_case <= t; test_case++) {
				coorsA = new int[]{1,1};
				coorsB = new int[]{10,10};
				sum = 0;
				
				input(br);
				pApply();
				fnc();
				
				sb.append("#"+ test_case + " " + sum + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pApply() {
		for (int i = 0; i < a; i++) {
			int y = aps[i][0];
			int x = aps[i][1];
			int c = aps[i][2];
			for (int i2 = c*(-1); i2 <= c; i2++) {
				for (int j = (c-Math.abs(i2))*(-1); j <= c-Math.abs(i2); j++) {
					if (!isIn(y+i2, x+j)) continue;
					p[i][y+i2][x+j] = true;
				}
			}
		}
	}
	
	public boolean isIn(int y, int x) {
		return y >= 1 && y <= 10 && x >= 1 && x <= 10;
	}
	
	public void input(BufferedReader br) throws IOException {
		String[] strs = br.readLine().split(" ");
		m = Integer.parseInt(strs[0]);
		a = Integer.parseInt(strs[1]);
		userA = new int[m];
		userB = new int[m];
		aps = new int[a][4];	// AP: y, x, 범위 C, 성능 P
		p = new boolean[a][11][11];
		
		strs = br.readLine().split(" ");
		for (int i = 0; i < m; i++) {
			userA[i] = Integer.parseInt(strs[i]);
		}
		strs = br.readLine().split(" ");
		for (int i = 0; i < m; i++) {
			userB[i] = Integer.parseInt(strs[i]);
		}
		
		for (int i = 0; i < a; i++) {
			strs = br.readLine().split(" ");
			aps[i][0] = Integer.parseInt(strs[1]);
			aps[i][1] = Integer.parseInt(strs[0]);
			aps[i][2] = Integer.parseInt(strs[2]);
			aps[i][3] = Integer.parseInt(strs[3]);
		}
	}
}
