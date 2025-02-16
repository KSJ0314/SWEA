import java.io.*;

public class Solution {
	static final int[][] dels = {null,{-1,0},{1,0},{0,-1},{0,1}};	// 상:1, 하:2, 좌:3, 우:4
	static int N,M,K, remain;
	static int[][][] prevArr, nextArr;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= t; test_case++) {
			init(br);
			
			fnc();
			
			sb.append("#"+test_case+" ").append(remain).append("\n");
		}
		System.out.println(sb.toString());
		
	}

	private static void fnc() {
		while (M-- >0) {
			moveMicrobe();
			selectDirection();
			prevArr = nextArr;
		}
	}

	private static void moveMicrobe() {
		nextArr = new int[N][N][2];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (prevArr[i][j][0] == 0) continue;
				int direction = prevArr[i][j][1];
				int[] del = dels[direction];
				
				int ni = i+del[0];
				int nj = j+del[1];
				nextArr[ni][nj][0] += prevArr[i][j][0];
				if (isEndLine(ni,nj)) {
					remain -= (nextArr[ni][nj][0]/2) + (nextArr[ni][nj][0]%2);
					nextArr[ni][nj][0] /= 2;
					
					nextArr[ni][nj][1] = direction;
					nextArr[ni][nj][1] += direction%2==0 ? -1 : +1;
				}
			}
		}
		
	}
	
	private static void selectDirection() {
		for (int i = 1; i < N-1; i++) {
			for (int j = 1; j < N-1; j++) {
				if (nextArr[i][j][0] == 0) continue;
				
				int nD = 0;
				int nC = 0;
				for (int k = 1; k < dels.length; k++) {
					int ni = i+dels[k][0];
					int nj = j+dels[k][1];
					int pc = prevArr[ni][nj][0];
					int pd = prevArr[ni][nj][1];
					if ((k == 1 && pd == 2)
							|| (k == 2 && pd == 1)
							|| (k == 3 && pd == 4)
							|| (k == 4 && pd == 3)) {
						if (nC < pc) {
							nC = pc;
							nD = pd;
						}
					}
				}
				nextArr[i][j][1] = nD;
			}
		}
	}
	
	public static boolean isEndLine(int y, int x) {
		return y==0 || y==N-1 || x==0 || x==N-1;
	}
	
	public static void init(BufferedReader br) throws IOException {
		String[] strs = br.readLine().split(" ");
		N = Integer.parseInt(strs[0]);
		M = Integer.parseInt(strs[1]);
		K = Integer.parseInt(strs[2]);
		prevArr = new int[N][N][2];
		remain = 0;
		
		for (int i = 0; i < K; i++) {
			strs = br.readLine().split(" ");
			int y = Integer.parseInt(strs[0]);
			int x = Integer.parseInt(strs[1]);
			int count = Integer.parseInt(strs[2]);
			int direction = Integer.parseInt(strs[3]);
			prevArr[y][x][0] = count;
			prevArr[y][x][1] = direction;
			
			remain += count;
		}
	}

}