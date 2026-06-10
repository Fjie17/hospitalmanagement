function goTo(role) {
	if (role === "patient") {
		window.location.href = "/register/patient";
	} else if (role === "doctor") {
		window.location.href = "/register/doctor";
	} else if (role === "admin") {
		window.location.href = "/admin";
	}
}

const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.dot');
let currentIndex = 0;
const total = slides.length;

function showSlide(index) {
	slides.forEach((slide, i) => {
		slide.classList.toggle('active', i === index);
	});
	dots.forEach((dot, i) => {
		dot.classList.toggle('active', i === index);
	});
	currentIndex = index;
}

// 自动轮播
setInterval(() => {
	let nextIndex = (currentIndex + 1) % total;
	showSlide(nextIndex);
}, 3000);

// 点击小白点切换
dots.forEach(dot => {
	dot.addEventListener('click', () => {
		showSlide(parseInt(dot.dataset.index));
	});
});

// 初始化显示第一个
showSlide(0);


// 可选功能：点击记录或跳转提示
document.querySelectorAll(".tip-image").forEach(img => {
	img.addEventListener("click", () => {
		console.log("用户点击了健康小知识图片");
	});
});


function askHealthAi() {
	const question = document.getElementById("ai-question").value;
	const answerDiv = document.getElementById("ai-answer");

	if (!question.trim()) {
		alert("请输入问题！");
		return;
	}

	answerDiv.classList.remove("d-none");
	answerDiv.innerHTML = "正在思考中，请稍候...";

	fetch("/api/ai/ask", {
		method: "POST",
		headers: {
			"Content-Type": "text/plain"
		},
		credentials: "include",
		body: question
	})
		.then(res => res.text())
		.then(data => {
			// 使用 marked.js 将 Markdown 转为 HTML
			answerDiv.innerHTML = marked.parse(data);
		})
		.catch(() => {
			answerDiv.innerHTML = "AI 接口调用失败，请稍后重试。";
		});
}

document.addEventListener("DOMContentLoaded", () => {
	searchTips(); // 页面加载时调用
});

async function searchTips() {
	const keyword = document.getElementById('search-input').value.trim();
	const url = keyword
		? `/api/health-tips/search?keyword=${encodeURIComponent(keyword)}`
		: `/api/health-tips/recent?limit=4`; // 无关键词则加载前4条

	const res = await fetch(url);
	if (res.ok) {
		const tips = await res.json();
		const container = document.getElementById('tips-container');
		container.innerHTML = '';
		if (tips.length === 0) {
			container.innerHTML = '<p>无匹配结果</p>';
			return;
		}
		tips.forEach(tip => {
			const tipHtml = `
                  <div class="health-tip-item">
                      <a href="${tip.videoUrl || '#'}" target="_blank" class="tip-image-wrapper">
                          <img src="${tip.imageUrl || '/default-image.png'}" alt="Health Image" class="tip-image rounded shadow" />
                      </a>
                      <div class="tip-content">
                          <h4 class="tip-title">${tip.title}</h4>
                          <p class="tip-summary">${tip.summary}</p>
                          <span class="tip-category">${tip.category}</span>
                      </div>
                  </div>
              `;
			container.insertAdjacentHTML('beforeend', tipHtml);
		});
	}
}

document.addEventListener("DOMContentLoaded", function() {
	const form = document.getElementById('contactForm');

	form.addEventListener('submit', function(e) {
		e.preventDefault();
		const formData = new FormData(this);

		fetch('/contact/submit', {
			method: 'POST',
			body: new URLSearchParams(formData),
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		})
			.then(res => {
				if (!res.ok) return res.text().then(err => { throw new Error(err); });
				return res.text();
			})
			.then(result => {
				if (result === 'success') {
					const modalEl = document.getElementById('successModal');
					const modal = new bootstrap.Modal(modalEl);
					modal.show();

					setTimeout(() => {
						modal.hide();
						form.reset();

						// ✅ 移除 modal 遮罩
						document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
						document.body.classList.remove('modal-open');
						document.body.style = '';

						// ✅ 彻底移除 modal DOM 元素
						modalEl.parentNode.removeChild(modalEl);

						// ✅ 跳转
						window.location.href = '/'; // 修改为你的首页路径
					}, 2000);
				} else {
					alert("❌ 提交失败：" + result);
				}
			})
			.catch(err => {
				alert("❌ 错误：" + err.message);
			});
	});
});

const backToTopBtn = document.getElementById('backToTopBtn');

window.addEventListener('scroll', () => {
	if (window.scrollY > 300) {
		backToTopBtn.classList.add('show');
	} else {
		backToTopBtn.classList.remove('show');
	}
});

backToTopBtn.addEventListener('click', () => {
	window.scrollTo({ top: 0, behavior: 'smooth' });
});
