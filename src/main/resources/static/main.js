$(document).ready(function() {
    $.ajax({
        type: 'GET',
        url: '/events/list',
        contentType: 'application/json',
        success: function(data) {
            const tableBody = $('#events-table tbody');
            data.forEach(event => {
                const row = `<tr>
                    <td>${event.type}</td>
                    <td>${event.message}</td>
                    <td>${event.userId}</td>
                    <td>${event.transactionId}</td>
                </tr>`;
                tableBody.append(row);
            });
        },
        error: function(error) {
            console.error('Error fetching events:', error);
        }
    });
});