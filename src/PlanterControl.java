
public class PlanterControl {
//
	// アイティプランターの栽培プログラムにある制御パラメータは、ここでまとめる
		private double brink=100.0;// White LED Blink ratio
		private double maxWaterLevel=500.0;
		private double minWaterLevel=200.0;
		private double returnAutoTime=60.0;
		private double returnManualTime=60.0;
		private double compansation=0.0;// 補正値
		
		public void setBrink(double brink) {
			this.brink = brink;
		}
		public double getBrink() {
			return brink;
		}
		public void setMaxWaterLevel(double maxWaterLevel) {
			this.maxWaterLevel = maxWaterLevel;
		}
		public double getMaxWaterLevel() {
			return maxWaterLevel;
		}
		public void setMinWaterLevel(double minWaterLevel) {
			this.minWaterLevel = minWaterLevel;
		}
		public double getMinWaterLevel() {
			return minWaterLevel;
		}
		public void setReturnAutoTime(double returnAutoTime) {
			this.returnAutoTime = returnAutoTime;
		}
		public double getReturnAutoTime() {
			return returnAutoTime;
		}
		public void setReturnManualTime(double returnManualTime) {
			this.returnManualTime = returnManualTime;
		}
		public double getReturnManualTime() {
			return returnManualTime;
		}
		public void setCompansation(double compansation) {
			this.compansation = compansation;
		}
		public double getCompansation() {
			return compansation;
		}
}
