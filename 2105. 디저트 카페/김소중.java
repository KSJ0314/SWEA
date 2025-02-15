import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


// X방향 사방 탐색
// 조건 1. 이미 지나간 숫자는 다시 지나갈 수 없다.
// 조건 2. 사각형으로 순회하여 시작 지점으로 돌아와야 한다.

// 1. => 지나간 숫자에 대한 b[]를 관리한다.
// 2. => 방향은 현재 idx의 del과 다음 idx의 del만 진행하면 된다. (사방 탐색x, 방향 종류는 4가지이지만 2방향 탐색)(사각형 순회, 도는 방향 상관 없음)
// 2. => 맨 처음 dfs()를 제외하고 현재 좌표가 시작 지점과 같은지 체크

// 관리할 변수
// test_case 초기화 : 배열 크기 n, 배열 숫자 정보 arr, 최대 거리 max
// for문 초기화 : visited, 시작 지점 startY|startX, 첫 dfs인지 (시작 지점 돌아오는거 체크)

// dfs 저장 값
// 거리
// 다음 진행 방향
// 지나간 숫자 정보

public class 김소중 {
	public static void main(String[] args) throws IOException {
		// System.setIn(new FileInputStream("./res/input.txt"));
		김소중 s = new 김소중();
		s.init();
		System.out.println(s.sb.toString());
	}
	
	StringBuilder sb = new StringBuilder();
	int[][] dels = { {1, 1} ,{ 1, -1 }, { -1, -1 }, { -1, 1 } };
	boolean[] visited;
	int[][] arr;
	int n, max, startY, startX;
	boolean isFirst;
	
	public void dfs(int y, int x, int count, int delIdx) {
		if (!isIn(y, x)) return;
		int num = arr[y][x];
		
		if (!isFirst && y == startY && x == startX) {
			max = Math.max(max, count);
			return;
		}
		
		if (visited[num]) {
			return;
		}
		
		visited[num] = true;
		int ni = delIdx;
		dfs(y + dels[ni][0], x + dels[ni][1], count+1, ni);
		
		ni = delIdx+1;
		isFirst = false;
		if (ni < 4) {
			dfs(y + dels[ni][0], x + dels[ni][1], count+1, ni);
		}
		visited[num] = false;
		
	}
	
	public void init() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			
			int t = Integer.parseInt(br.readLine());
			for (int test_case = 1; test_case <= t; test_case++) {
				max = -1;
				input(br);
				
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						visited = new boolean[101];
						startY = i;
						startX = j;
						isFirst = true;
						
						dfs(i, j, 0, 0);
					}
				}
				
				sb.append("#"+ test_case + " " + max + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isIn(int y, int x) {
		return y >= 0 && y < n && x >= 0 && x < n;
	}
	
	public void input(BufferedReader br) throws IOException {
		n = Integer.parseInt(br.readLine());
		arr = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			String[] strs = br.readLine().split(" ");
			for (int j = 0; j < n; j++) {
				arr[i][j] = Integer.parseInt(strs[j]);
			}
		}
	}
}
