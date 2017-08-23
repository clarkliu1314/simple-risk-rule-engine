function Rules(id, no, name, priority, level, interfaceName, status, startTime,
		endTime,startInterval,endInterval) {
	this.id = id;
	this.no = no;
	this.name = name; 
	this.priority = priority;
	this.level = level;
	this.interfaceName = interfaceName;
	this.status = status;
	this.startTime = startTime;
	this.endTime = endTime;
	this.startInterval = startInterval;
	this.endInterval = endInterval;
}

function Condition(factor,externalParam,operator, checkValue, riskFactorParam, connector) {
	this.riskFactor = factor;
	this.externalParam = externalParam;
	this.checkCondition = operator;
	this.checkValue = checkValue;
	this.riskFactorParam = riskFactorParam;
	this.connector = connector;
}

function Handler(command, commandValue) {
	this.command = command;
	this.commandValue = commandValue;
}