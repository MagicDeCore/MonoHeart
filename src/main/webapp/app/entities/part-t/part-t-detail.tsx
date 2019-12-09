import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './part-t.reducer';
import { IPartT } from 'app/shared/model/part-t.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPartTDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PartTDetail extends React.Component<IPartTDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { partTEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            PartT [<b>{partTEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{partTEntity.name}</dd>
            <dt>Module T</dt>
            <dd>
              {partTEntity.moduleTS
                ? partTEntity.moduleTS.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === partTEntity.moduleTS.length - 1 ? '' : ', '}
                    </span>

                  ))
                : null}

            </dd>
          </dl>
          <Button tag={Link} to="/part-t" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/part-t/${partTEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ partT }: IRootState) => ({
  partTEntity: partT.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PartTDetail);
