function loadContent(url) {
    $("#main-content").load(url, function(response, status) {
        if (status !== "success") {
            const modal = new bootstrap.Modal(document.getElementById("infoModal"));
            document.getElementById("modalBody").innerText = "加载失败，请稍后再试。";
            modal.show();
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const now = new Date();
    const options = { year: "numeric", month: "long", day: "numeric", weekday: "long" };
    document.getElementById("current-date").innerText = now.toLocaleDateString("zh-CN", options);
});

function toggleSection(header) {
    const section = header.closest('.sidebar-section');
    const icon = header.querySelector('.toggle-icon');
    const list = section.querySelector('.sidebar-list');

    if (list.classList.contains('collapsed')) {
        list.classList.remove('collapsed');
        icon.classList.remove('rotated');
        section.classList.remove('collapsed');
    } else {
        list.classList.add('collapsed');
        section.classList.add('collapsed');
    }
}

// 点击用户名加载个人信息页面
function loadUserInfo() {
    loadContent("/patient/profile");
}

function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle("collapsed");
}

function loadUserInfo() {
        // AJAX请求示例，加载个人信息到主内容区
        $.ajax({
            url: '/patient/profile', // 你自己改成获取个人信息的接口
            method: 'GET',
            success: function(data) {
                $('#main-content').html(data);
            },
            error: function() {
                alert('加载个人信息失败，请稍后重试');
            }
        });
    }
	function loadMedicalRecords() {
	    $.ajax({
	        url: '/patient/medical-records',
	        method: 'GET',
	        success: function(data) {
	            $('#main-content').html(data);
	        },
	        error: function() {
	            const modal = new bootstrap.Modal(document.getElementById("infoModal"));
	            document.getElementById("modalBody").innerText = "加载历史病历失败，请稍后重试。";
	            modal.show();
	        }
	    });
	}


function loadAppointments() {
    $.ajax({
        url: '/patient/appointments',
        method: 'GET',
        success: function(data) {
            $('#main-content').html(data);
        },
        error: function() {
            alert('加载预约记录失败，请稍后再试。');
        }
    });
}

document.addEventListener("DOMContentLoaded", () => {
    loadSidebarTips(); // 页面加载时默认加载 3 条
});

async function loadSidebarTips(keyword = '') {
    const url = keyword
        ? `/api/health-tips/search?keyword=${encodeURIComponent(keyword)}`
        : `/api/health-tips/recent?limit=3`;

    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error('加载失败');
        const tips = await res.json();
        const container = document.getElementById('sidebar-tips');
        container.innerHTML = '';

        if (tips.length === 0) {
            container.innerHTML = '<p class="text-muted">无匹配结果</p>';
            return;
        }

        tips.forEach(tip => {
            const summaryText = tip.summary?.length > 40 ? tip.summary.slice(0, 40) + '...' : tip.summary || '';
            const html = `
                <div class="mb-3 small">
                    <a href="${tip.videoUrl || '#'}" target="_blank" class="fw-semibold text-dark d-block mb-1">
                        ${tip.title}
                    </a>
                    <span class="text-muted">${summaryText}</span>
                </div>
            `;
            container.insertAdjacentHTML('beforeend', html);
        });
    } catch (e) {
        document.getElementById('sidebar-tips').innerHTML = '<p class="text-danger">加载失败</p>';
        console.error(e);
    }
}

function searchSidebarTips() {
    const keyword = document.getElementById('sidebar-search').value.trim();
    loadSidebarTips(keyword);
}

function showAiModal() {
    const modal = new bootstrap.Modal(document.getElementById('aiModal'));
    modal.show();
}

function askHealthAiFromModal() {
    const question = document.getElementById("aiModalQuestion").value.trim();
    const answerBox = document.getElementById("aiModalAnswer");

    if (!question) {
        answerBox.classList.remove("d-none");
        answerBox.classList.add("alert-warning");
        answerBox.innerText = "请输入问题后再点击提问。";
        return;
    }

    answerBox.classList.remove("d-none", "alert-warning");
    answerBox.classList.add("alert-info");
    answerBox.innerText = "正在思考中，请稍候...";

    // 发送 AJAX 请求到后端
    fetch('/api/ai/ask', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ question: question })
    })
    .then(response => response.json())
    .then(data => {
        answerBox.innerHTML = data.answer ? formatAiAnswer(data.answer) : "未能获取回答。";
    })
    .catch(() => {
        answerBox.classList.remove("alert-info");
        answerBox.classList.add("alert-danger");
        answerBox.innerText = "出错了，请稍后再试。";
    });
}

// 可选：对 AI 返回内容格式化
function formatAiAnswer(answer) {
    return answer
        .replace(/#+\s?/g, '') // 去除多余的 markdown #
        .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
        .replace(/\n/g, '<br>');
}


function loadNoticeDetail(id) {
    $.ajax({
        url: '/patient/notice/detail/' + id,
        type: 'GET',
        success: function (data) {
            $('#main-content').html(data);
        },
        error: function () {
            showModalError('公告详情加载失败，请稍后再试。');
        }
    });
}

function showModalError(msg) {
    $('#modalBody').text(msg);
    $('#infoModal').modal('show');
}

function loadNoticeList() {
    $.ajax({
        url: '/patient/notice/list',
        type: 'GET',
        success: function (data) {
            $('#main-content').html(data);
        },
        error: function () {
            showModalError('公告列表加载失败，请稍后再试。');
        }
    });
}



