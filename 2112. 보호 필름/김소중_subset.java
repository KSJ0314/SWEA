import java.io.*;
import java.util.Arrays;

public class 김소중_subset {
	static int D,W,K,min;
	static int[][] arr;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= t; test_case++) {
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
			
			// 부분 집합의 size를 설정해서 작은 크기의 부분 집합부터 구한다.
			for (int i = 0; i <= K; i++) {
				if (subset(0,0,i)) break;
			}
			
			sb.append("#"+test_case+" "+min+"\n");
		}
		
		System.out.println(sb.toString());
	}
	
	// boolean으로 return해서 check()가 true일 때 연달아서 다 종료됨
	public static boolean subset(int depth, int cnt, int size) {
		if (cnt == size) {
			min = size;
			return check();
		}
		if (depth == D) return false;
		if (D-depth+cnt < size) return false;
		
		// 미리 배열 복사
		int[] temp = arr[depth].clone();
		
		// A로 가득 채워서 진행
		Arrays.fill(arr[depth], 0);
		if (subset(depth+1, cnt+1, size)) return true;
		
		// B로 가득 채워서 진행
		Arrays.fill(arr[depth], 1);
		if (subset(depth+1, cnt+1, size)) return true;
		
		// 아무것도 하지 않고 진행 (복사한 값 다시 채워줌으로써 원본 배열을 복구)
		arr[depth] = temp;
		if (subset(depth+1, cnt, size)) return true;
		
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

}