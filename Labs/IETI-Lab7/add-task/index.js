module.exports = async function (context, req) {
    context.log('Adding a task to the planner');

    const task = req.body;
	
	//Se genera la id para simular la persistencia del objeto.
	const { v4: uuidv4 } = require('uuid');
	const taskId = uuidv4();
	//Se retorna el objeto con el id generado.
	const newTask = {...task, id:taskId};

    context.res = {
        status: 201,
        body: newTask
    };
}