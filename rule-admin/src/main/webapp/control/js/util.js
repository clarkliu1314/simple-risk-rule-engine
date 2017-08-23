function compDate(beginTime, endTime) {
	var arr = beginTime.split("-");
	var starttime = new Date(arr[0], arr[1], arr[2]);
	var starttimes = starttime.getTime();

	var arrs = endTime.split("-");
	var lktime = new Date(arrs[0], arrs[1], arrs[2]);
	var lktimes = lktime.getTime();

	if (starttimes >= lktimes) {

		alert('有效期结束时间不能小于开始时间!');
		return false;
	} else
		return true;

}

function compTime(beginTime, endTime) {
	var bt = beginTime.split(':');
	var et = endTime.split(':');
	var a = Date.UTC(0, 0, 0, bt[0], bt[1], 0);
	var b = Date.UTC(0, 0, 0, et[0], et[1], 0);
	if (b < a) {
		alert('有效时段结束时间不能小于起始时间!');
		return false;
	}
	return true;
}