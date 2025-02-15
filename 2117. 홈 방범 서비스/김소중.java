import java.io.*;

// [제약사항]
// 1. 시간제한 : 최대 50개 테스트 케이스를 모두 통과하는데, C/C++/Java 모두 3초
// 2. 도시의 크기 N은 5 이상 20 이하의 정수이다. (5 ≤ N ≤ 20)
// 3. 하나의 집이 지불할 수 있는 비용 M은 1 이상 10 이하의 정수이다. (1 ≤ M ≤ 10)
// 4. 홈방범 서비스의 운영 비용은 서비스 영역의 면적과 동일하다.
// 5. 도시의 정보에서 집이 있는 위치는 1이고, 나머지는 0이다.
// 6. 도시에는 최소 1개 이상의 집이 존재한다.

// [출력]
// 손해를 보지 않으면서 홈방범 서비스를 가장 많은 집들에 제공하는 서비스 영역을 찾았을 때,
// 그 때의 서비스를 제공 받는 집들의 수이다.
public class 김소중 {
	static int N,M, count, max;
	static int[][] arr;
	
	public static void main(String[] args) throws IOException {
		// System.setIn(new FileInputStream("./res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= t; test_case++) {
			String[] strs = br.readLine().split(" ");
			N = Integer.parseInt(strs[0]);
			M = Integer.parseInt(strs[1]);
			arr = new int[N][N];
			max = Integer.MIN_VALUE;
			count = 0;
			
			for (int i = 0; i < N; i++) {
				strs = br.readLine().split(" ");
				for (int j = 0; j < N; j++) {
					int num = Integer.parseInt(strs[j]);
					arr[i][j] = num;
					if (num == 1) count++;
				}
			}
			
			int size = 0;
			W: while(true) {
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (check(i, j, size)) break W;
					}
				}
				size++;
			}
			
			sb.append("#"+ test_case + " " + max + "\n");
		}
		System.out.println(sb.toString());
		
	}

	static boolean check(int y, int x, int size) {
		int sum = 0;
		int cnt = 0;
		int cost = 0;
		for (int i = size*(-1); i <= size; i++) {
			for (int j = (size-Math.abs(i))*(-1); j <= (size-Math.abs(i)); j++) {
				cost++;
				sum--;
				if (!isIn(y+i,x+j)) continue;
				if (arr[y+i][x+j] == 1) {
					cnt++;
					sum += M;
				}
			}
		}
		
		if (sum >= 0) {
			max = Math.max(max, cnt);
		}
		
		if (count == cnt) return true;
		if (cost > count*M) return true;
		return false;
	}
	
	static boolean isIn(int y, int x) {
		return y>=0 && y <N && x>=0 && x<N;
	}
	
}