// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

let typeData = [];
document.querySelectorAll('.reservationList').forEach((item) => {
  // 각 .reservationList 요소에서 input 값 가져오기
  const type = item.querySelector('input[type="text"]').value; // 첫 번째 input의 값
  const num = item.querySelector('input[type="number"]').value; // 두 번째 input의 값
  typeData.push({ type: type, num: Number(num) }); // Number()로 point를 숫자로 변환
});
console.log(typeData);

// Pie Chart Example
var ctx = document.getElementById("myPieChart");

// type을 라벨로 사용하고 num을 데이터로 사용
const chartLabels = typeData.map(data => data.type); // 라벨 배열
const dataValues = typeData.map(data => data.num); // 데이터 배열

// 배경색을 동적으로 생성 (최대 4개 색상 설정)
const backgroundColors = ['#4e73df', '#1cc88a', '#36b9cc', '#f6c23e', '#e74a3b', '#858796', '#f8c7a2']; // 추가 색상
const selectedColors = backgroundColors.slice(0, chartLabels.length); // 현재 type의 개수에 맞게 색상 선택

var myPieChart = new Chart(ctx, {
  type: 'doughnut',
  data: {
    labels: chartLabels, // 라벨 배열
    datasets: [{
      data: dataValues, // 데이터 배열
      backgroundColor: selectedColors, // 동적으로 선택된 배경색
      hoverBackgroundColor: selectedColors.map(color => color.replace('0.8', '1')), // 호버 색상 변경
      hoverBorderColor: "rgba(234, 236, 244, 1)",
    }],
  },
  options: {
    maintainAspectRatio: false,
    tooltips: {
      backgroundColor: "rgb(255,255,255)",
      bodyFontColor: "#858796",
      borderColor: '#dddfeb',
      borderWidth: 1,
      xPadding: 15,
      yPadding: 15,
      displayColors: false,
      caretPadding: 10,
    },
    legend: {
      display: false
    },
    cutoutPercentage: 80,
  },
});

// 범례 추가 (선택 사항)
const legendContainer = document.getElementById('pieChartLegend');
chartLabels.forEach((label, index) => {
  const legendItem = document.createElement('span');
  legendItem.className = 'mr-2';
  legendItem.innerHTML = `<i class="fas fa-circle" style="color: ${selectedColors[index]}"></i> ${label}`;
  legendContainer.appendChild(legendItem);
});
