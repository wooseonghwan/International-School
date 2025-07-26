let reserveNmInput = $("input[name=searchVal]");
$(document).ready(function () {

    window.addEventListener('load', function () {
        const parseJsonSafe = (raw) => {
            try {
                return JSON.parse(raw);
            } catch {
                return [];
            }
        };

        const productSalesEl = document.getElementById('product-sales-data'); // 숨김 div 접근
        const productSalesLabels = parseJsonSafe(productSalesEl.dataset.labels); // TITLE 리스트
        const productSalesCounts = parseJsonSafe(productSalesEl.dataset.counts); // 판매 건수 리스트

        console.log('productSalesLabels:', productSalesLabels);
        console.log('productSalesCounts:', productSalesCounts);
        // 2. 랜덤 색상 만들기
        function getRandomColor() {
            const letters = '0123456789ABCDEF';
            let color = '#';
            for (let i = 0; i < 6; i++) {
                color += letters[Math.floor(Math.random() * 16)];
            }
            return color;
        }

        const productColors = productSalesLabels.map(() => getRandomColor());

        // 3. 상품별 판매율 차트 그리기
        const csData = {
            labels: productSalesLabels,
            datasets: [
                {
                    data: productSalesCounts,
                    backgroundColor: productColors,
                    borderWidth: 0,
                },
            ],
        };

        var csPieChart = new Chart(document.getElementById('cs-pie-chart'), {
            type: 'pie',
            data: csData,
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: { display: false },
                    tooltip: { enabled: true },
                },
            },
        });

        // 4. 상품 범례(legend) 리스트 동적으로 만들기
        const productLegendList = document.querySelector('.legend-list ul.product');
        productLegendList.innerHTML = '';

        productSalesLabels.forEach((label, index) => {
            const li = document.createElement('li');

            const box = document.createElement('span');
            box.className = 'box';
            box.style.backgroundColor = productColors[index];

            const title = document.createElement('span');
            title.className = 'title';
            title.innerText = label;

            li.appendChild(box);
            li.appendChild(title);
            productLegendList.appendChild(li);
        });

        // 유입경로 (파이 차트)
        const trafficItems  = document.querySelectorAll('.legend-list ul.traffic li');
        const trafficLabels = [];
        const trafficData = [];
        const trafficColors = [];

        trafficItems.forEach(item => {
            const label = item.querySelector('.title')?.innerText.trim();
            const color = item.querySelector('.box')?.style.backgroundColor;
            const count = parseInt(item.dataset.count || '0', 10);

            if (label && color && count > 0) {
                trafficLabels.push(label);
                trafficData.push(count);
                trafficColors.push(color);
            }
        });

        new Chart(document.getElementById('traffic-pie-chart'), {
            type: 'pie',
            data: {
                labels: trafficLabels,
                datasets: [{
                    label: '유입경로 분석',
                    data: trafficData,
                    backgroundColor: trafficColors,
                    borderWidth: 0,
                }],
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: { display: false },
                    tooltip: { enabled: true },
                },
            },
        });
        // 유입경로 (파이 차트) 끝

        // 일별 매출표
        const barItems = document.querySelectorAll('.bar-data-list li');
        const barLabels = Array.from({ length: 31 }, (_, i) => String(i + 1));
        const barDataMap = new Map();

        barItems.forEach(item => {
            const label = String(item.dataset.label).trim().replace(/^0+/, '');
            const count = parseInt(item.dataset.count?.replace(/,/g, '') || '0', 10);
            if (label && !isNaN(count)) {
                barDataMap.set(label, count);
            }
        });
        const barData = barLabels.map(label => {
            const val = barDataMap.get(label);
            return val || 0;
        });

        const barCtx = document.getElementById('mybarChart').getContext('2d');

        new Chart(barCtx, {
            type: 'bar',
            data: {
                labels: barLabels,
                datasets: [{
                    label: '일별 매출',
                    data: barData,
                    backgroundColor: '#CDD1D6',
                    borderColor: '#CDD1D6',
                    barThickness: 6,
                }],
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        min: 0,
                        max: 10000000,
                        ticks: {
                            stepSize: 2000000,
                            callback: value => value.toLocaleString() + '원',
                        },
                    },
                },
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false },
                    tooltip: {
                        callbacks: {
                            label: context => `${context.raw.toLocaleString()}원`
                        }
                    }
                },
            },
        });

        // 방문자 수
        const dataEl = document.getElementById('visit-data');

       /* const parseJsonSafe = (raw) => {
            try {
                return JSON.parse(raw);
            } catch {
                return [];
            }
        };*/

        const dailyLabels = parseJsonSafe(dataEl.dataset.dailyLabels);
        const dailyData = parseJsonSafe(dataEl.dataset.dailyData);
        const weeklyLabels = parseJsonSafe(dataEl.dataset.weeklyLabels);
        const weeklyData = parseJsonSafe(dataEl.dataset.weeklyData);
        const monthlyLabels = parseJsonSafe(dataEl.dataset.monthlyLabels);
        const monthlyData = parseJsonSafe(dataEl.dataset.monthlyData);

        let rawMaterialChart = createMaterialChart(dailyLabels, dailyData);

        document.querySelectorAll('.people-wrap .tab').forEach((tab, index) => {
            tab.addEventListener('click', () => {
                document.querySelectorAll('.people-wrap .tab').forEach(t => t.classList.remove('active'));
                tab.classList.add('active');

                let labels, data;
                if (index === 0) {
                    labels = dailyLabels;
                    data = dailyData;
                } else if (index === 1) {
                    labels = weeklyLabels;
                    data = weeklyData;
                } else {
                    labels = monthlyLabels;
                    data = monthlyData;
                }

                rawMaterialChart.data.labels = labels;
                rawMaterialChart.data.datasets[0].data = data;
                rawMaterialChart.data.datasets[0].backgroundColor = data.map((_, i) => i === data.length - 1 ? '#14907F' : '#eee');
                rawMaterialChart.update();
            });
        });

        function createMaterialChart(labels, data) {
            const ctx = document.getElementById('rawMaterialSales').getContext('2d');
            return new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        data: data,
                        backgroundColor: data.map((_, i) => i === data.length - 1 ? '#14907F' : '#eee'),
                        borderWidth: 1,
                        barThickness: 30,
                        maxBarThickness: 40,
                        categoryPercentage: 0.6,
                        barPercentage: 0.6
                    }],
                },
                options: {
                    plugins: {
                        legend: { display: false },
                    },
                    scales: {
                        x: {
                            grid: { display: false },
                            offset: true,
                        },
                        y: {
                            beginAtZero: true,
                            ticks: {
                                stepSize: 100,
                                callback: value => value,
                            },
                        },
                    },
                    responsive: true,
                    maintainAspectRatio: false,
                }
            });
        }
    });
});
$("span[data-role=noticeDetail]").click(function(){
    let noticeId = $(this).data("notice-id");
    location.href = `/bo/homepage/notice/detail?noticeId=` + noticeId;
});