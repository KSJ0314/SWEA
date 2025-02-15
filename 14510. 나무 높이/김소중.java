import java.util.Arrays;
import java.util.Scanner;

public class 김소중 {
	public static void main(String args[]) throws Exception {

		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			int n = sc.nextInt();
			int[] arr = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = sc.nextInt();
			}
			Arrays.sort(arr);
			
			int odd = 0;	// 홀수
			int even = 0;	// 짝수
			for (int i = 0; i < n-1; i++) {
				int diff = arr[n-1] - arr[i];
				if (diff == 0) continue;
				if (diff % 2 != 0) {
					odd++; 
				}
				even += (diff/2)*2;
			}
			
			int result = 0;
			boolean isOdd = true;
			boolean oddMinus = false;
			
			while (odd > 0 || even > 0) {
				if (isOdd) {
					odd--;
					if (odd < 0) {
						if (oddMinus) {
							even -= 2;
						}
						oddMinus = !oddMinus;
					}
				} else {
					even-=2;
				}
				
				result++;
				isOdd = !isOdd;
			}
			
			System.out.println("#" + test_case + " " + result);
		}
	}
}