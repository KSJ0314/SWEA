import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// 조건 1. 우 | 하 배열 탐색
// 조건 2. 다음 좌표의 값의 "차이"는 1보다 크면 안된다
// 조건 3. 다음 좌표와의 차이가 1인 경우 : x값을 넘겨주고 진행, x를 하나씩 빼가면서 다음 값이 같은지 확인

// h가 0으로 시작 

// h <= 0
// -> diff == 0 -> h--
// -> diff == 1 -> h를 this.h로 설정

// h > 0 이면
// -> diff가 무조건 0이어야함

// diff가 1보다 크면 절대 불가능

public class 김소중 {
	public static void main(String[] args) throws IOException {
		// System.setIn(new FileInputStream("./res/input.txt"));
		김소중 s = new 김소중();
		s.init();
		System.out.println(s.sb.toString());
	}

	StringBuilder sb = new StringBuilder();
	int[][] dels = {{1,0},{0,1}};
	int[][] arr;
	int n, h, count, sameCnt, remain;

	public void fnc(int y, int x, int delIdx) {
		remain++;
		if (remain > 0) {
			sameCnt++;
		}

		int ny = y + dels[delIdx][0];
		int nx = x + dels[delIdx][1];
		
		if (!isIn(ny, nx)) {
			if (remain >= 0) {
				count++;
			}
			return;
		}
		
		int num1 = arr[y][x];
		int num2 = arr[ny][nx];

		int diff = Math.abs(num2 - num1);
		if (diff > 1) {
			return;
		}
		if (diff == 1) {
			if (remain < 0) return;
			if (num2 > num1) {	// 다음 값이 더 큼
				if (sameCnt < this.h) {
					return;
				}
			} else {	// 다음 값이 더 작음
				remain = (-1) * this.h;
			}
			sameCnt = 0;
		}
		
		fnc(ny, nx, delIdx);
	}

	public void init() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			int t = Integer.parseInt(br.readLine());
			for (int test_case = 1; test_case <= t; test_case++) {
				count = 0;

				input(br);
				for (int i = 0; i < n; i++) {
					sameCnt = 0;
					remain = 1;
					fnc(0, i, 0);
					sameCnt = 0;
					remain = 1;
					fnc(i, 0, 1);
				}

				sb.append("#" + test_case + " " + count + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isIn(int y, int x) {
		return y >= 0 && y < n && x >= 0 && x < n;
	}

	public void input(BufferedReader br) throws IOException {
		String[] strs = br.readLine().split(" ");
		n = Integer.parseInt(strs[0]);
		h = Integer.parseInt(strs[1]);
		arr = new int[n][n];

		for (int i = 0; i < n; i++) {
			strs = br.readLine().split(" ");
			for (int j = 0; j < n; j++) {
				arr[i][j] = Integer.parseInt(strs[j]);
			}
		}
	}
}