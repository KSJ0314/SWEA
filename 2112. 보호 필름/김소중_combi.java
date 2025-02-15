import java.io.*;
import java.util.Arrays;

public class 김소중_combi {
	static int D,W,K,min, size;
	static int[][] arr;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= t; test_case++) {
			init(br);
			
			// 부분 집합의 size를 설정해서 작은 크기의 부분 집합부터 구한다.
			for (int i = 0; i <= K; i++) {
				size = i;
				if (combi(0, 0)) {
					min = i;
					break;
				}
			}
			
			sb.append("#"+test_case+" ").append(min).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	// boolean으로 return해서 check()가 true일 때 연달아서 다 종료됨
	public static boolean combi(int depth, int start) {
		if (depth == size) return check();
		
		for (int i = start; i < D; i++) {
			// 미리 배열 복사
			int[] temp = arr[i].clone();
			
			// A로 가득 채워서 진행
			Arrays.fill(arr[i], 0);
			if (combi(depth+1, i+1)) return true;
			
			// B로 가득 채워서 진행
			Arrays.fill(arr[i], 1);
			if (combi(depth+1, i+1)) return true;
			
			arr[i] = temp;
		}
		
		return false;
	}
	
	public static boolean check() {
		O: for (int i = 0; i < W; i++) {
			int cnt = 1;
			int temp = arr[0][i];
			
			for (int j = 1; j < D; j++) {
				int n = arr[j][i];
				cnt = (n == temp) ? cnt+1 : 1;
				if (cnt == K) {
					continue O;
				}
				temp = n;
			}
			return false;
		}
		return true;
	}
	
	public static void init(BufferedReader br) throws IOException {
		String[] strs = br.readLine().split(" ");
		D = Integer.parseInt(strs[0]);
		W = Integer.parseInt(strs[1]);
		K = Integer.parseInt(strs[2]);
		arr = new int[D][W];
		min = -1;
		
		for (int i = 0; i < D; i++) {
			strs = br.readLine().split(" ");
			for (int j = 0; j < W; j++) {
				arr[i][j] = Integer.parseInt(strs[j]);
			}
		}
	}

}