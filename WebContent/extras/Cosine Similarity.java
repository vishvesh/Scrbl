	
	public class CosineSimilarity {
		static void Main() {
			int[] vecA = { 1, 2, 3, 4, 5 };
			int[] vecB = { 6, 7, 7, 9, 10 };
	
			var cosSimilarity = CalculateCosineSimilarity(vecA, vecB);
	
			Console.WriteLine(cosSimilarity);
			Console.Read();
		}
	
		private static double CalculateCosineSimilarity(int[] vecA, int[] vecB) {
			var dotProduct = DotProduct(vecA, vecB);
			var magnitudeOfA = Magnitude(vecA);
			var magnitudeOfB = Magnitude(vecB);
	
			return dotProduct / (magnitudeOfA * magnitudeOfB);
		}
	
		private static double DotProduct(int[] vecA, int[] vecB) {
			// I'm not validating inputs here for simplicity.
			double dotProduct = 0;
			for (var i = 0; i < vecA.Length; i++) {
				dotProduct += (vecA[i] * vecB[i]);
			}
		}
		
		private static double Magnitude(int[] vector)
		{
			return Math.Sqrt(DotProduct(vector, vector));
		}
	}