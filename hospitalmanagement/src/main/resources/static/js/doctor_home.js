function toggleSidebar() {
        const sidebar = document.getElementById("sidebar");
        if (sidebar.style.display === "none") {
            sidebar.style.display = "block";
        } else {
            sidebar.style.display = "none";
        }
    }
	
	function loadContent(url) {
	    $("#main-content").load(url, function(response, status) {
	        if (status !== "success") {
	            alert("内容加载失败，请稍后重试！");
	        } else {
	            if (url === "/doctor/schedule/page") {
	                loadSchedule();
	            }
	        }
	    });
	}

	function loadSchedule() {
	    $.ajax({
	        url: "/doctor/schedule",
	        method: "GET",
	        success: function(schedules) {
	            let tbody = $("#schedule-table-body");
	            tbody.empty();
	            if (schedules.length === 0) {
	                tbody.append("<tr><td colspan='4'>暂无排班信息</td></tr>");
	                return;
	            }
	            schedules.forEach(s => {
	                tbody.append(`<tr>
	                    <td>${s.workDate}</td>
	                    <td>${s.timeSlot}</td>
	                    <td>${s.maxPatients}</td>
	                    <td>${s.currentPatients}</td>
	                </tr>`);
	            });
	        },
	        error: function() {
	            alert("加载排班信息失败！");
	        }
	    });
	}
	
	$(document).ready(function () {
	    // 获取 CSRF token 和 header 名称
	    const tokenMeta = $("meta[name='_csrf']");
	    const headerMeta = $("meta[name='_csrf_header']");

	    const token = tokenMeta.length ? tokenMeta.attr("content") : null;
	    const header = headerMeta.length ? headerMeta.attr("content") : null;

	    $(document).on("click", ".save-btn", function () {
	        const id = $(this).data("id");
	        const status = $(`.status-select[data-id='${id}']`).val();
	        const note = $(`.note-input[data-id='${id}']`).val();

	        $.ajax({
	            type: "POST",
	            url: "/doctor/appointments/update",
	            data: {
	                appointmentId: id,
	                status: status,
	                note: note
	            },
	            beforeSend: function (xhr) {
	                if (token && header) {
	                    xhr.setRequestHeader(header, token); // 设置 CSRF
	                }
	            },
	            success: function () {
	                alert("保存成功！");
	            },
	            error: function () {
	                alert("保存失败！");
	            }
	        });
	    });
	});
	
	$(document).on("submit", "#record-form", function (e) {
	    e.preventDefault();
	    $.ajax({
	        type: "POST",
	        url: "/doctor/records/save",
	        data: $(this).serialize(),
	        success: function (res) {
	            $("#save-result").html("<div class='alert alert-success'>保存成功！</div>");
	        },
	        error: function () {
	            $("#save-result").html("<div class='alert alert-danger'>保存失败！</div>");
	        }
	    });
	});

