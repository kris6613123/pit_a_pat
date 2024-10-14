// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

function number_format(number, decimals, dec_point, thousands_sep) {
  number = (number + '').replace(',', '').replace(' ', '');
  var n = !isFinite(+number) ? 0 : +number,
      prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
      sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
      dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
      s = '',
      toFixedFix = function(n, prec) {
        var k = Math.pow(10, prec);
        return '' + Math.round(n * k) / k;
      };
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || '').length < prec) {
    s[1] = s[1] || '';
    s[1] += new Array(prec - s[1].length + 1).join('0');
  }
  return s.join(dec);
}

// 날짜별로 포인트를 합산하는 함수
function aggregatePoints(data) {
  const result = {};

  data.forEach(entry => {
    const date = entry.regDttm.split("T")[0]; // 날짜만 추출
    const point = entry.point;
    const day = new Date(date).getDate();
    // 해당 날짜에 포인트를 추가
    if (!result[day]) {
      result[day] = 0; // 초기화
    }
    result[day] += point; // 포인트 합산
  });

  return result;
}


let addList = document.querySelectorAll('.addList');
let addData= [];
addList.forEach((item) => {
  // 각 .addList 요소에서 input 값 가져오기
  const regDttm = item.querySelector('input[type="text"]').value; // 첫 번째 input의 값
  const point = item.querySelector('input[type="number"]').value; // 두 번째 input의 값
  addData.push({ regDttm: regDttm, point: Number(point) }); // Number()로 point를 숫자로 변환
});
let subList = document.querySelectorAll('.subList');
let subData= [];
subList.forEach((item) => {
  // 각 .addList 요소에서 input 값 가져오기
  const regDttm = item.querySelector('input[type="text"]').value; // 첫 번째 input의 값
  const point = item.querySelector('input[type="number"]').value; // 두 번째 input의 값
  subData.push({ regDttm: regDttm, point: Number(point) }); // Number()로 point를 숫자로 변환
});
// 포인트 집계
const aggregatedAddData = aggregatePoints(addData);
const aggregatedSubData = aggregatePoints(subData);
// 날짜와 포인트 배열로 변환
const labels = Object.keys(aggregatedAddData);
// 적립 데이터는 labels 기준으로 이미 맞춰져 있음
const pointsAdd = labels.map(label => aggregatedAddData[label]);
// 차감 데이터도 labels에 맞춰 매핑, 없으면 0으로 처리
const pointsSub = labels.map(label => aggregatedSubData[label] || 0);

// Create a new line chart
var ctx = document.getElementById("myAreaChart");
var myLineChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: labels, // 집계된 날짜
    datasets: [{
      label: "적립",
      lineTension: 0.3,
      backgroundColor: "rgba(78, 115, 223, 0.05)",
      borderColor: "rgba(78, 115, 223, 1)",
      pointRadius: 3,
      pointBackgroundColor: "rgba(78, 115, 223, 1)",
      pointBorderColor: "rgba(78, 115, 223, 1)",
      data: pointsAdd, // 집계된 포인트 for addData
    },
      {
        label: "차감",
        lineTension: 0.3,
        backgroundColor: "rgba(255, 99, 132, 0.05)", // Red background
        borderColor: "rgba(255, 99, 132, 1)", // Red border
        pointRadius: 3,
        pointBackgroundColor: "rgba(255, 99, 132, 1)", // Red points
        pointBorderColor: "rgba(255, 99, 132, 1)", // Red border points
        data: pointsSub, // 집계된 포인트 for subData
      }],
  },
  options: {
    maintainAspectRatio: false,
    layout: {
      padding: {
        left: 10,
        right: 25,
        top: 25,
        bottom: 0
      }
    },
    scales: {
      xAxes: [{
        gridLines: {
          display: false,
          drawBorder: false
        },
        ticks: {
          // Customize the tick labels
          callback: function(value, index, values) {
            const dateParts = value.split("-"); // Assuming the format is "YYYY-MM-DD"
            const day = parseInt(dateParts[2], 10); // Get the day part

            if (day === 1 || day === 15 || day === new Date(dateParts[0], dateParts[1], 0).getDate()) {
              return day + "일"; // Return only 1st, 15th, and last day
            }
            return ''; // Hide other days
          },
          maxTicksLimit: 7 // Limit the number of ticks shown
        }
      }],
      yAxes: [{
        ticks: {
          maxTicksLimit: 5,
          padding: 10,
          callback: function(value, index, values) {
            return '$' + number_format(value);
          }
        },
        gridLines: {
          color: "rgb(234, 236, 244)",
          zeroLineColor: "rgb(234, 236, 244)",
          drawBorder: false,
          borderDash: [2],
          zeroLineBorderDash: [2]
        }
      }],
    },
    legend: {
      display: true
    },
    tooltips: {
      backgroundColor: "rgb(255,255,255)",
      bodyFontColor: "#858796",
      titleMarginBottom: 10,
      titleFontColor: '#6e707e',
      titleFontSize: 14,
      borderColor: '#dddfeb',
      borderWidth: 1,
      xPadding: 15,
      yPadding: 15,
      displayColors: false,
      intersect: false,
      mode: 'index',
      caretPadding: 10,
      callbacks: {
        title: function(tooltipItems) {
          return ''; // 빈 문자열을 반환하여 타이틀을 숨김
        },
        label: function(tooltipItem, chart) {
          var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
          var dayLabel = tooltipItem.xLabel + '일'; // Append '일' to the x-axis label
          return datasetLabel + ': ' + dayLabel + ' - $' + number_format(tooltipItem.yLabel);
        }
      }
    }
  }
});

