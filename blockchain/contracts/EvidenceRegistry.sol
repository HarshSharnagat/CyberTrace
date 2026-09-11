// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract EvidenceRegistry {
    struct Evidence { bytes32 evidenceHash; uint256 timestamp; address registrar; }
    mapping(bytes32 => Evidence) public records;
    event EvidenceRegistered(bytes32 indexed caseId, bytes32 indexed evidenceHash, uint256 timestamp, address registrar);
    function registerEvidence(bytes32 caseId, bytes32 evidenceHash) external {
        require(records[caseId].timestamp == 0, "Case already registered");
        records[caseId] = Evidence(evidenceHash, block.timestamp, msg.sender);
        emit EvidenceRegistered(caseId, evidenceHash, block.timestamp, msg.sender);
    }
    function verifyEvidence(bytes32 caseId, bytes32 evidenceHash) external view returns (bool) {
        return records[caseId].timestamp != 0 && records[caseId].evidenceHash == evidenceHash;
    }
}
